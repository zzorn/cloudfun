#!/bin/bash

MONGODB_VERSION=1.6.0
PLATFORM_32=linux-i686
PLATFORM_64=linux-x86_64

echo "### Installing MongoDB"

# Create dir
echo "### Creating mongo/ directory"
mkdir mongo
cd mongo

# Detect the architecture and load correct mongodb
if $(uname -a | grep 'x86_64') || $(uname -a | grep 'ia64')
then
  echo "### 64 bit os detected, installing 64 bit mongodb"
  MONGODB_NAME=mongodb-$PLATFORM_64-$MONGODB_VERSION
else
  echo "### 32 bit os detected, installing 32 bit mongodb."
  echo "### WARNING: Database will be restricted to 2 GB, 64 bit machine recommended!"
  MONGODB_NAME=mongodb-$PLATFORM_32-$MONGODB_VERSION
fi

# Download
echo "### Downloading mongodb"
wget http://downloads.mongodb.org/linux/$MONGODB_NAME.tgz -O mongo.tgz

# Exctract mongodb
echo "### Extracting mongodb"
tar -xzvf mongo.tgz
rm mongo.tgz

# Alias
echo "### Creating mongodb alias"
ln -s $MONGODB_NAME mongodb

cd ..

# Create a data dir
echo "### Creating data/db/ directory"
mkdir -p data/db/

echo "### End of Mongodb installation"
