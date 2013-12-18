// -*- Java -*-
/*!
 * @file  FaceGetValImpl.java
 * @brief ModuleDescription
 * @date  $Date$
 * @author romau
 * @url http://www.sic.shibaura-it.ac.jp/~ma13014/
 * $Id$
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.IntegerHolder;
import jp.go.aist.rtm.RTC.util.StringHolder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import RTC.ReturnCode_t;
import RTC.Time;
import RTC.TimedDouble;
import RTC.TimedString;

/*!
 * @class FaceGetValImpl
 * @brief ModuleDescription
 *
 */
public class FaceGetValImpl extends DataFlowComponentBase {

	double before = 0;

	/*
	 * !
	 * 
	 * @brief constructor
	 * 
	 * @param manager Maneger Object
	 */
	public FaceGetValImpl(Manager manager) {
		super(manager);
		// <rtc-template block="initializer">
		// m_url_val = new TimedString();
		// m_url = new DataRef<TimedString>(m_url_val);
		// m_urlIn = new InPort<TimedString>("url", m_url);
		m_val_val = new TimedDouble();
		m_val = new DataRef<TimedDouble>(m_val_val);
		m_valOut = new OutPort<TimedDouble>("val", m_val);
		// </rtc-template>

	}

	/*
	 * !
	 * 
	 * The initialize action (on CREATED->ALIVE transition) formaer
	 * rtc_init_entry()
	 * 
	 * @return RTC::ReturnCode_t
	 */
	@Override
	protected ReturnCode_t onInitialize() {
		// Registration: InPort/OutPort/Service
		// <rtc-template block="registration">
		// Set InPort buffers
		// addInPort("url", m_urlIn);

		// Set OutPort buffer
		addOutPort("val", m_valOut);
		// </rtc-template>
		bindParameter("threshold", m_threshold, "1");
		bindParameter("url", m_url, "http://wasanbon.org");
		return super.onInitialize();
	}

	/***
	 * 
	 * The finalize action (on ALIVE->END transition) formaer
	 * rtc_exiting_entry()
	 * 
	 * @return RTC::ReturnCode_t
	 * 
	 * 
	 */
	// @Override
	// protected ReturnCode_t onFinalize() {
	// return super.onFinalize();
	// }

	/***
	 * 
	 * The startup action when ExecutionContext startup former
	 * rtc_starting_entry()
	 * 
	 * @param ec_id
	 *            target ExecutionContext Id
	 * 
	 * @return RTC::ReturnCode_t
	 * 
	 * 
	 */
	// @Override
	// protected ReturnCode_t onStartup(int ec_id) {
	// return super.onStartup(ec_id);
	// }

	/***
	 * 
	 * The shutdown action when ExecutionContext stop former
	 * rtc_stopping_entry()
	 * 
	 * @param ec_id
	 *            target ExecutionContext Id
	 * 
	 * @return RTC::ReturnCode_t
	 * 
	 * 
	 */
	// @Override
	// protected ReturnCode_t onShutdown(int ec_id) {
	// return super.onShutdown(ec_id);
	// }

	/***
	 * 
	 * The activated action (Active state entry action) former
	 * rtc_active_entry()
	 * 
	 * @param ec_id
	 *            target ExecutionContext Id
	 * 
	 * @return RTC::ReturnCode_t
	 * 
	 * 
	 */
	@Override
	protected ReturnCode_t onActivated(int ec_id) {
		return super.onActivated(ec_id);
	}

	/***
	 * 
	 * The deactivated action (Active state exit action) former
	 * rtc_active_exit()
	 * 
	 * @param ec_id
	 *            target ExecutionContext Id
	 * 
	 * @return RTC::ReturnCode_t
	 * 
	 * 
	 */
	@Override
	protected ReturnCode_t onDeactivated(int ec_id) {
		return super.onDeactivated(ec_id);
	}

	/***
	 * 
	 * The execution action that is invoked periodically former rtc_active_do()
	 * 
	 * @param ec_id
	 *            target ExecutionContext Id
	 * 
	 * @return RTC::ReturnCode_t
	 * 
	 * 
	 */
	@Override
	protected ReturnCode_t onExecute(int ec_id) {
		int likes = 0, comment = 0;
		String url = "null";
		Time tm01 = new Time(0, 0);
		Time tm02 = new Time(0, 0);
		TimedDouble subVal01 = new TimedDouble(tm01, likes);
		TimedDouble subVal02 = new TimedDouble(tm02, comment);
		TimedString subData = new TimedString();

		URL fql;
		try {
			fql = new URL(
					"https://api.facebook.com/method/fql.query?query=select%20%20like_count,%20share_count,%20comment_count,%20total_count%20from%20link_stat%20where%20url=%22"
							+ m_url.value + "%22");

			// System.out.println("url_fql is " + fql);

			HttpURLConnection http = (HttpURLConnection) fql.openConnection();
			http.setRequestMethod("GET");
			http.connect();

			DocumentBuilderFactory dbfactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = dbfactory.newDocumentBuilder();
			Document doc = builder.parse(http.getInputStream());

			Element root = doc.getDocumentElement();
			// System.out.println(root.getNodeName());

			NodeList nl1 = root.getElementsByTagName("link_stat");
			Node nd1 = nl1.item(0);
			// System.out.println(nd1.getNodeName());

			NodeList nl2 = ((Element) nd1).getElementsByTagName("like_count");
			System.out.println("like_count = "
					+ nl2.item(0).getFirstChild().getNodeValue());

			NodeList nl3 = ((Element) nd1)
					.getElementsByTagName("comment_count");

			subVal01.data = Integer.parseInt(nl2.item(0).getFirstChild()
					.getNodeValue());
			subVal02.data = Integer.parseInt(nl3.item(0).getFirstChild()
					.getNodeValue());
			System.out.println("before = " + before);
			if (before != subVal01.data) {
				m_valOut.write(subVal01);
				before = subVal01.data;
				System.out.println("likes = " + subVal01.data);
				System.out.println("comment = " + subVal02.data);
			}
			http.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.onExecute(ec_id);
	}

	/***
	 * 
	 * The aborting action when main logic error occurred. former
	 * rtc_aborting_entry()
	 * 
	 * @param ec_id
	 *            target ExecutionContext Id
	 * 
	 * @return RTC::ReturnCode_t
	 * 
	 * 
	 */
	// @Override
	// public ReturnCode_t onAborting(int ec_id) {
	// return super.onAborting(ec_id);
	// }

	/***
	 * 
	 * The error action in ERROR state former rtc_error_do()
	 * 
	 * @param ec_id
	 *            target ExecutionContext Id
	 * 
	 * @return RTC::ReturnCode_t
	 * 
	 * 
	 */
	// @Override
	// public ReturnCode_t onError(int ec_id) {
	// return super.onError(ec_id);
	// }

	/***
	 * 
	 * The reset action that is invoked resetting This is same but different the
	 * former rtc_init_entry()
	 * 
	 * @param ec_id
	 *            target ExecutionContext Id
	 * 
	 * @return RTC::ReturnCode_t
	 * 
	 * 
	 */
	// @Override
	// protected ReturnCode_t onReset(int ec_id) {
	// return super.onReset(ec_id);
	// }

	/***
	 * 
	 * The state update action that is invoked after onExecute() action no
	 * corresponding operation exists in OpenRTm-aist-0.2.0
	 * 
	 * @param ec_id
	 *            target ExecutionContext Id
	 * 
	 * @return RTC::ReturnCode_t
	 * 
	 * 
	 */
	// @Override
	// protected ReturnCode_t onStateUpdate(int ec_id) {
	// return super.onStateUpdate(ec_id);
	// }

	/***
	 * 
	 * The action that is invoked when execution context's rate is changed no
	 * corresponding operation exists in OpenRTm-aist-0.2.0
	 * 
	 * @param ec_id
	 *            target ExecutionContext Id
	 * 
	 * @return RTC::ReturnCode_t
	 * 
	 * 
	 */
	// @Override
	// protected ReturnCode_t onRateChanged(int ec_id) {
	// return super.onRateChanged(ec_id);
	// }
	//
	// Configuration variable declaration
	// <rtc-template block="config_declare">
	/*
	 * !
	 * 
	 * - Name: threshold - DefaultValue: 1
	 */
	protected IntegerHolder m_threshold = new IntegerHolder();
	/*
	 * !
	 * 
	 * - Name: threshold - DefaultValue: 1
	 */
	protected StringHolder m_url = new StringHolder(); // </rtc-template>

	// DataInPort declaration
	// <rtc-template block="inport_declare">
	// protected TimedString m_url_val;
	// protected DataRef<TimedString> m_url;
	/*
	 * !
	 */
	// protected InPort<TimedString> m_urlIn;

	// </rtc-template>

	// DataOutPort declaration
	// <rtc-template block="outport_declare">
	protected TimedDouble m_val_val;
	protected DataRef<TimedDouble> m_val;
	/*
	 * !
	 */
	protected OutPort<TimedDouble> m_valOut;

	// </rtc-template>

	// CORBA Port declaration
	// <rtc-template block="corbaport_declare">

	// </rtc-template>

	// Service declaration
	// <rtc-template block="service_declare">

	// </rtc-template>

	// Consumer declaration
	// <rtc-template block="consumer_declare">

	// </rtc-template>

}
