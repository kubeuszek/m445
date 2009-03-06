package br.unifor.mia.cd.middleware.rme2.server;

import arcademis.*;
import arcademis.server.*;
import br.unifor.mia.cd.middleware.rme2.*;
import br.unifor.mia.cd.middleware.rme2.rmep.*;

public class RmeResponseSender extends ResponseSender {

	private Identifier callIdentifier = null;

	private Stream returnValue = null;

	public void open(Channel ch) {
		try {
			super.protocol.setChannel(ch);
			RetMsg returnMsg = (RetMsg)OrbAccessor.getMessage(RmeConstants.RETURN_MESSAGE);
			returnMsg.setCallIdentifier(callIdentifier);
			returnMsg.setReturnValue(returnValue);

			super.protocol.send(returnMsg);
		} catch (ArcademisException e) {
			e.printStackTrace();
		}
	}

	public void setCallIdentifier(Identifier callIdentifier) {
		this.callIdentifier = callIdentifier;
	}

	public Identifier getCallIdentifier() {
		return this.callIdentifier;
	}

	public void setReturnValue(Stream returnValue) {
		this.returnValue = returnValue;
	}

	public Stream getReturnValue() {
		return this.returnValue;
	}
}
