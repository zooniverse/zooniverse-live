// Compiled by ClojureScript 0.0-2311
goog.provide('zooniverse_live.core');
goog.require('cljs.core');
goog.require('clojure.string');
goog.require('om.dom');
goog.require('om.dom');
goog.require('clojure.string');
goog.require('om.core');
goog.require('om.core');
cljs.core.enable_console_print_BANG_.call(null);
zooniverse_live.core.update_state = (function update_state(app){return (function (msg){cljs.core.println.call(null,msg.data);
var msg__$1 = clojure.string.trim_newline.call(null,msg.data);if(cljs.core._EQ_.call(null,msg__$1,"Heartbeat"))
{return null;
} else
{return cljs.core.swap_BANG_.call(null,app,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"classifiers","classifiers",-126806737)], null),cljs.core.conj,cljs.core.js__GT_clj.call(null,JSON.parse(msg__$1)).call(null,"project"));
}
});
});
zooniverse_live.core.connect_websocket = (function connect_websocket(app){var socket = (new WebSocket("ws://event.zooniverse.org/classifications"));socket.onmessage = zooniverse_live.core.update_state.call(null,app);
return socket;
});
zooniverse_live.core.app_state = cljs.core.atom.call(null,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"classifiers","classifiers",-126806737),cljs.core.PersistentVector.EMPTY], null));
zooniverse_live.core.classifier_view = (function classifier_view(classifier,owner){if(typeof zooniverse_live.core.t7701 !== 'undefined')
{} else
{
/**
* @constructor
*/
zooniverse_live.core.t7701 = (function (owner,classifier,classifier_view,meta7702){
this.owner = owner;
this.classifier = classifier;
this.classifier_view = classifier_view;
this.meta7702 = meta7702;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
zooniverse_live.core.t7701.cljs$lang$type = true;
zooniverse_live.core.t7701.cljs$lang$ctorStr = "zooniverse-live.core/t7701";
zooniverse_live.core.t7701.cljs$lang$ctorPrWriter = (function (this__4120__auto__,writer__4121__auto__,opt__4122__auto__){return cljs.core._write.call(null,writer__4121__auto__,"zooniverse-live.core/t7701");
});
zooniverse_live.core.t7701.prototype.om$core$IRender$ = true;
zooniverse_live.core.t7701.prototype.om$core$IRender$render$arity$1 = (function (this$){var self__ = this;
var this$__$1 = this;return React.DOM.li(null,self__.classifier);
});
zooniverse_live.core.t7701.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_7703){var self__ = this;
var _7703__$1 = this;return self__.meta7702;
});
zooniverse_live.core.t7701.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_7703,meta7702__$1){var self__ = this;
var _7703__$1 = this;return (new zooniverse_live.core.t7701(self__.owner,self__.classifier,self__.classifier_view,meta7702__$1));
});
zooniverse_live.core.__GT_t7701 = (function __GT_t7701(owner__$1,classifier__$1,classifier_view__$1,meta7702){return (new zooniverse_live.core.t7701(owner__$1,classifier__$1,classifier_view__$1,meta7702));
});
}
return (new zooniverse_live.core.t7701(owner,classifier,classifier_view,null));
});
zooniverse_live.core.classifiers_view = (function classifiers_view(app,owner){if(typeof zooniverse_live.core.t7707 !== 'undefined')
{} else
{
/**
* @constructor
*/
zooniverse_live.core.t7707 = (function (owner,app,classifiers_view,meta7708){
this.owner = owner;
this.app = app;
this.classifiers_view = classifiers_view;
this.meta7708 = meta7708;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
zooniverse_live.core.t7707.cljs$lang$type = true;
zooniverse_live.core.t7707.cljs$lang$ctorStr = "zooniverse-live.core/t7707";
zooniverse_live.core.t7707.cljs$lang$ctorPrWriter = (function (this__4120__auto__,writer__4121__auto__,opt__4122__auto__){return cljs.core._write.call(null,writer__4121__auto__,"zooniverse-live.core/t7707");
});
zooniverse_live.core.t7707.prototype.om$core$IRender$ = true;
zooniverse_live.core.t7707.prototype.om$core$IRender$render$arity$1 = (function (this$){var self__ = this;
var this$__$1 = this;return React.DOM.div(null,React.DOM.h1(null,"Classifiers"),cljs.core.apply.call(null,om.dom.ul,null,om.core.build_all.call(null,zooniverse_live.core.classifier_view,new cljs.core.Keyword(null,"classifiers","classifiers",-126806737).cljs$core$IFn$_invoke$arity$1(self__.app))));
});
zooniverse_live.core.t7707.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_7709){var self__ = this;
var _7709__$1 = this;return self__.meta7708;
});
zooniverse_live.core.t7707.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_7709,meta7708__$1){var self__ = this;
var _7709__$1 = this;return (new zooniverse_live.core.t7707(self__.owner,self__.app,self__.classifiers_view,meta7708__$1));
});
zooniverse_live.core.__GT_t7707 = (function __GT_t7707(owner__$1,app__$1,classifiers_view__$1,meta7708){return (new zooniverse_live.core.t7707(owner__$1,app__$1,classifiers_view__$1,meta7708));
});
}
return (new zooniverse_live.core.t7707(owner,app,classifiers_view,null));
});
om.core.root.call(null,zooniverse_live.core.classifiers_view,zooniverse_live.core.app_state,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"target","target",253001721),document.getElementById("app")], null));
zooniverse_live.core.connect_websocket.call(null,zooniverse_live.core.app_state);

//# sourceMappingURL=core.js.map