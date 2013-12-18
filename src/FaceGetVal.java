// -*- Java -*-
/*!
 * @file FaceGetVal.java
 * @date $Date$
 *
 * $Id$
 */

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;
import jp.go.aist.rtm.RTC.RegisterModuleFunc;
import jp.go.aist.rtm.RTC.util.Properties;

/*!
 * @class FaceGetVal
 * @brief ModuleDescription
 */
public class FaceGetVal implements RtcNewFunc, RtcDeleteFunc, RegisterModuleFunc {

//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
    	    "implementation_id", "FaceGetVal",
    	    "type_name",         "FaceGetVal",
    	    "description",       "ModuleDescription",
    	    "version",           "1.0.0",
    	    "vendor",            "romau",
    	    "category",          "e-neSpeaker",
    	    "activity_type",     "COMMUTATIVE",
    	    "max_instance",      "1",
    	    "language",          "Java",
    	    "lang_type",         "compile",
            // Configuration variables
            "conf.default.threshold", "1",
            // Widget
            "conf.__widget__.threshold", "text",
            // Constraints
    	    ""
            };
//  </rtc-template>

    public RTObject_impl createRtc(Manager mgr) {
        return new FaceGetValImpl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
    public void registerModule() {
        Properties prop = new Properties(component_conf);
        final Manager manager = Manager.instance();
        manager.registerFactory(prop, new FaceGetVal(), new FaceGetVal());
    }
}
