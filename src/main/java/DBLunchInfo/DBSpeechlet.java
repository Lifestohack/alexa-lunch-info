package DBLunchInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

import Models.Days;
import Models.Menus;

public class DBSpeechlet implements SpeechletV2 {
	private static final Logger logger = LoggerFactory
			.getLogger(DBSpeechlet.class);

	public void onSessionStarted(
			SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
		logger.info("onSessionStarted requestId={}, sessionId={}",
				requestEnvelope.getRequest().getRequestId(), requestEnvelope
						.getSession().getSessionId());
	}

	public SpeechletResponse onLaunch(
			SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
		logger.info("onLaunch requestId={}, sessionId={}", requestEnvelope
				.getRequest().getRequestId(), requestEnvelope.getSession()
				.getSessionId());
		return getMenusResponse();
	}

	public void onSessionEnded(
			SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
		logger.info("onSessionEnded requestId={}, sessionId={}",
				requestEnvelope.getRequest().getRequestId(), requestEnvelope
						.getSession().getSessionId());

	}

	public SpeechletResponse onIntent(
			SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		IntentRequest request = requestEnvelope.getRequest();
		logger.info("onIntent requestId={}, sessionId={}", request
				.getRequestId(), requestEnvelope.getSession().getSessionId());

		Intent intent = request.getIntent();
		String intentName = (intent != null) ? intent.getName() : null;

		if ("LunchIntent".equals(intentName)) {
			return getMenusResponse();
		} else if ("AMAZON.HelpIntent".equals(intentName)) {
			return getHelpResponse();
		} else if ("AMAZON.StopIntent".equals(intentName)) {
			return stopIntent();
		} else {
			return getAskResponse("Unsupported",
					"This is unsupported.  Please try something else.");
		}
	}

	private SpeechletResponse stopIntent() {
		String speechText = "Good Appetite.";
		return getAskResponse("Stop", speechText);
	}

	private SpeechletResponse getMenusResponse() {
		logger.info("There was a Error.");
		String speechText = null;
		try{
		speechText =ReadAndParsePDF.getInstance().getMenu(Menus.BISTRO, Days.FREITAG);
		}catch(Exception e){
			logger.error(e.toString());
			speechText = "Sorry, No food for you.";
		}
		return getAskResponse("Bistro", "Bistro hat folgende essen heute: " + speechText);
	}

	private SpeechletResponse getHelpResponse() {
		String speechText = "Try saying Menu one or Menu two.";
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

}
