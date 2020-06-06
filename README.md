# Minecraft Chat Relay plugin

![Java CI with Gradle](https://github.com/Robbe7730/MinecraftChatRelay/workflows/Java%20CI%20with%20Gradle/badge.svg)

## Introduction

This plugin was originally written to be just a bridge between Discord and Minecraft chat, but has been written to be more expandable for platforms (forge, bukkit, fabric) as well as chatting applications (Discord, Slack, ...).

## Adding a chat service

Simply create a new class extending ChatService and add it to ChatPlugin. That's it, all messages from other platforms will now be relayed to your newly implemented chat service.

## Adding a platform

This is still highly work in progress, currently it is only implemented for FabricMC, implementing for a new service _should_ be limited to re-implementing the mixins. What we need is a way to call the callbacks (that also need minor changes) and everything else shouldn't be platform-dependant.

## Project Structure

### callbacks

This package contains all the callbacks. In the FabricMC API these callbacks are also the listeners, so I've matched my implementation to theirs.

### commands

This package contains all the Minecraft-commands, mainly used for debugging. (currently only `/sendall <message>`) that sends `<message>` to all available platfroms.

### entities

This package contains all the entities, not Minecraft-entities, but objects containing data. Creating separate classes for this instead of re-using the platform's classes makes it more abstract and easier to port.

### mixins

FabricMC-specific. This package contians all the mixins that FabricMC uses.

### repositories

This package contains the different Data-repositories.

### services

This package contains the different chat services. Currently Minecraft-chat and Discord are supported.

### util

This package contains general utility-classes.

