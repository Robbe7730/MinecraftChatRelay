# Minecraft Chat Relay plugin

![Java CI with Gradle](https://github.com/Robbe7730/MinecraftChatRelay/workflows/Java%20CI%20with%20Gradle/badge.svg)

## Introduction

This plugin was originally written to be just a bridge between Discord and Minecraft chat, but has been written to be
more expandable for platforms (forge, bukkit, fabric) as well as chatting applications (Discord, Slack, ...).

## Running

Installation can be done as any normal Fabric mod.

Configuration can be done in `config/chatplugin.conf`. 

## Adding a chat service

Simply create a new class extending ChatService and add it to ChatPlugin. Note that every service is responsible for not
forwarding its own messages.

## Adding a platform

This is still highly work in progress, currently it is only implemented for FabricMC, implementing for a new service
_should_ be limited to re-implementing the mixins. What we need is a way to call the callbacks (that also need minor
changes) and everything else shouldn't be platform-dependant.

## Project Structure

### entities

This package contains all the entities, not Minecraft-entities, but objects containing data. Creating separate classes
for this instead of re-using the platform's classes makes it more abstract and easier to port.

### enums

This package contains (as the name implies) the enums used in MinecraftChatRelay.

### internals

This contains all the Minecraft-internal classes, some of this may be reused, other things may need to be reimplemented
for new platforms.

The main distinction here is that this only contains commands related to MinecraftChatRelay itself, not the ChatService.
This is why the `/verify` command is in `services.minecraft` and `/disable` is in `internals`. `/verify` is only used by
the Minecraft chat service, but  `/disable` works for all chat services.

### repositories

This package contains the different Data-repositories.

### services

This package contains the different chat services. Currently Minecraft-chat and Discord are supported.

The minecraft service also contains the callbacks that the mixins call.

### util

This package contains general utility-classes.

