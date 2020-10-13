#!/bin/sh

# Decrypt the file
mkdir $HOME/secrets
# --batch to prevent interactive command
# --yes to assume "yes" for questions
gpg --quiet --batch --yes --decrypt --passphrase="$MAPBOX_DOWNLOADS_TOKEN" \
--output $HOME/secrets/mapbox_token.json mapbox_token.json.gpg
