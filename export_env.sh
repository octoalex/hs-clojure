#!/bin/bash

set -e
set -u

pwd=$(pwd)

if test -f "$pwd/.env"; then
    input="$pwd/.env"
else
    input="$pwd/.env.example"    
fi


input="$pwd/.env"
while IFS= read -r line
do
  echo "EXPORT $line"
done < "$input"