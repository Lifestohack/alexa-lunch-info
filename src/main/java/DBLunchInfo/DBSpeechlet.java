package DBLunchInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class DBSpeechlet implements SpeechletV2 {
	private static final Logger logger = LoggerFactory.getLogger(DBSpeechlet.class);

	private static final String MENUS_ITEMS = "MenuItems";
	private static final String PRICE_ITEMS = "PriceItems";

	public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
		logger.info("onSessionStarted requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
				requestEnvelope.getSession().getSessionId());
	}

	public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
		logger.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
				requestEnvelope.getSession().getSessionId());

		return toSpeechletResponse("Welcome", "Willkommen!!! Was möchtest du essen?");
	}

	@SuppressWarnings("unused")
	public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
		SessionEndedRequest sessionEndedRequest = requestEnvelope.getRequest();
		Session session = requestEnvelope.getSession();

		logger.info("onSessionEnded requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
				requestEnvelope.getSession().getSessionId());
	}

	public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		IntentRequest request = requestEnvelope.getRequest();
		logger.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
				requestEnvelope.getSession().getSessionId());

		Intent intent = request.getIntent();
		String intentName = (intent != null) ? intent.getName() : null;
		logger.info("Running Inten: " + intentName);
		if ("LunchIntent".equals(intentName)) {
			return getLunchIntentResponse(intent);
		} else if ("AMAZON.HelpIntent".equals(intentName)) {
			return getHelpResponse();
		} else if ("AMAZON.StopIntent".equals(intentName)) {
			return getStopResponse("Stop", "Guten Appetit!");
		} else if ("PriceIntent".equals(intentName)) {
			return getPriceIntentResponse(intent);
		} else {

			return getAskResponse("Unsupported", "Entschuldigung! Ich habe nicht verstanden. Welche menü meintest du?");
		}
	}

	private SpeechletResponse getStopResponse(String title, String speechText) {
		logger.info("Running stopIntent Funtion.");
		SimpleCard card = getSimpleCard(title, speechText);
		PlainTextOutputSpeech speech = getPlainTextOutputSpeech(speechText);
		logger.info("Running stopIntent Funtion.");
		return SpeechletResponse.newTellResponse(speech, card);
	}

	private SpeechletResponse getLunchIntentResponse(Intent intent) {
		logger.info("Starting getMenusResponse for new request for food.");
		String speechText = DBResponse.getMenuResponseFromIntent(intent, MENUS_ITEMS, PRICE_ITEMS, false );
		logger.info("getMenusResponse: " + speechText);
		logger.info("Ending getMenusResponse for new request for food. " + speechText);
		return getAskResponse("Mittag essen", speechText);
	}

	private SpeechletResponse getPriceIntentResponse(Intent intent) {
		logger.info("Starting LunchIntent for new request for food.");
		String speechText = DBResponse.getMenuResponseFromIntent(intent, MENUS_ITEMS, PRICE_ITEMS, true );
		logger.info("getMenusResponse: " + speechText);
		logger.info("Ending LunchIntent for new request for food. " + speechText);
		return getAskResponse("Mittag essen", speechText);
	}
	
	
	private SpeechletResponse getHelpResponse() {
		String speechText = "Du hast folgende Menü zur auswahl: suppe, menü eins, menü zwei, menü drei, aktion, bistro und dessert. Du kannst auch nach dem Preis fragen.";
		logger.info("getHelpResponse: " + speechText);
		return getAskResponse("Mittag essen", speechText);
	}

	private SpeechletResponse getAskResponse(String cardTitle, String speechText) {
		SimpleCard card = getSimpleCard(cardTitle, speechText);
		PlainTextOutputSpeech speech = getPlainTextOutputSpeech(speechText);
		Reprompt reprompt = getReprompt(speech);
		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}

	private Reprompt getReprompt(PlainTextOutputSpeech outputSpeech) {
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(outputSpeech);
		return reprompt;
	}

	private PlainTextOutputSpeech getPlainTextOutputSpeech(String speechText) {
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);
		return speech;
	}

	private SimpleCard getSimpleCard(String cardTitle, String speechText) {
		SimpleCard card = new SimpleCard();
		card.setTitle(cardTitle);
		card.setContent(speechText);

		return card;
	}

	private SpeechletResponse toSpeechletResponse(String title, String speechText) {
		return getAskResponse(title, speechText);
	}

}
