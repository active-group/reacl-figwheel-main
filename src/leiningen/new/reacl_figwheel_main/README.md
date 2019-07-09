# {{name}}

FIXME: Write a one-line description of your reacl application.

## Overview

FIXME: Write a paragraph about the reacl application's and highlight its goals.

## Development

### Build

To build the project and start a fighweel main server & repl:

```
lein fig:build
```

A browser window will open (http://localhost:9500) and the application is shown. If you change your code, the application will automatically be updated in the browser.


### Test

Since `auto-testing` is set to `true` in the dev.cljs.edn file, tests are executed when running `lein fig:build`. Test results can be seen under http://localhost:9500/figwheel-extra-main/auto-testing


To just test the project type

```
lein fig:test
```


### Production build

To build for production with advanced optimization run

```
lein fig:min
```



## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
