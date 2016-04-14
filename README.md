# valcan bot

# api
- /api/bot/

# heroku

- heroku create
- heroku config:set GRADLE_TASK="bootRepackage"
- heroku config:set LINE_CHANNEL_ID="..."
- heroku config:set LINE_CHANNEL_SECRET="..."
- heroku config:set LINE_CHANNEL_MID="..."
- heroku config:set PROXY_HOST=""
- heroku config:set PROXY_PORT=""
- heroku config:set PROXY_USER=""
- heroku config:set PROXY_PASS=""
- heroku addons:create fixie:tricycle
- git push heroku master
- heroku logs -t
