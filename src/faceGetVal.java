// -*- Java -*-
/*!
 * @file faceGetVal.java
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
 * @class faceGetVal
 * @brief ModuleDescription
 */
public class faceGetVal implements RtcNewFunc, RtcDeleteFunc, RegisterModuleFunc {

//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
    	    "implementation_id", "faceGetVal",
    	    "type_name",         "faceGetVal",
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
        return new faceGetValImpl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
    public void registerModule() {
        Properties prop = new Properties(component_conf);
        final Manager manager = Manager.instance();
        manager.registerFactory(prop, new faceGetVal(), new faceGetVal());
    }
}
