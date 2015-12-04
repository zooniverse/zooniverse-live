zooniverse-live
===============

Rebuilt Zooniverse Live using the new Event Stream


### prerequisites

Follow the instructions [here](https://github.com/technomancy/leiningen#installation) to install Java and Leiningen

### development

Zooniverse-live uses [lein-figwheel](https://github.com/bhauman/lein-figwheel) for live reloading in development

Run `lein clean` and then `lein figwheel dev` to run figwheel and start up a server & browser connected repl. And then navigate to `localhost:3449`

### production build

Run `lein clean` and then `lein cljsbuild once min` to build the site into a single js file with advanced optimizations. Everything will be built into resources/public as a root directory, the same as in development
