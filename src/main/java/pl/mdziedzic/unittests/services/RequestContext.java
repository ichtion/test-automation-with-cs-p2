package pl.mdziedzic.unittests.services;

import java.util.Calendar;

import pl.mdziedzic.unittests.User;

public class RequestContext {

	private User user;
	private Calendar now;
	private HistoryProvider historyProvider;
	private MailProvider mailProvider;

	public RequestContext(User user) {
		this.user = user;

		now = Calendar.getInstance();

		HistoryCollector collector = new HistoryCollector();
		historyProvider = new SimpleHistoryProvider(collector);
		mailProvider = new SimpleMailProvider();
	}

	public User getUser() {
		return user;
	}

	public Calendar getNow() {
		return now;
	}

	public HistoryProvider getHistoryProvider() {
		return historyProvider;
	}

	public MailProvider getMailProvider() {
		return mailProvider;
	}

}
