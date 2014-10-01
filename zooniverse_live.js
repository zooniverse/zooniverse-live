goog.addDependency("base.js", ['goog'], []);
goog.addDependency("../cljs/core.js", ['cljs.core'], ['goog.string', 'goog.object', 'goog.string.StringBuffer', 'goog.array']);
goog.addDependency("../om/dom.js", ['om.dom'], ['cljs.core']);
goog.addDependency("../om/core.js", ['om.core'], ['cljs.core', 'om.dom', 'goog.ui.IdGenerator']);
goog.addDependency("../clojure/string.js", ['clojure.string'], ['goog.string', 'cljs.core', 'goog.string.StringBuffer']);
goog.addDependency("../zooniverse_live/websocket.js", ['zooniverse_live.websocket'], ['cljs.core', 'om.dom', 'om.core', 'clojure.string']);
goog.addDependency("../zooniverse_live/classifiers_view.js", ['zooniverse_live.classifiers_view'], ['cljs.core', 'om.dom', 'om.core', 'clojure.string']);
goog.addDependency("../zooniverse_live/project_list.js", ['zooniverse_live.project_list'], ['cljs.core', 'om.dom', 'om.core', 'clojure.string']);
goog.addDependency("../zooniverse_live/core.js", ['zooniverse_live.core'], ['cljs.core', 'om.dom', 'zooniverse_live.project_list', 'zooniverse_live.classifiers_view', 'om.core', 'zooniverse_live.websocket', 'clojure.string']);