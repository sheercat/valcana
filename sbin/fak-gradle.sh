pkill -f gradle; rm -rf ~/.m2/repository/ ~/.gradle/caches/ .gradle; ./gradlew --recompile-scripts --refresh-dependencies --rerun-tasks clean check
