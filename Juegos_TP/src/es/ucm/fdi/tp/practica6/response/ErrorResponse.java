package es.ucm.fdi.tp.practica6.response;

import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;

public class ErrorResponse implements Response{
	
	private String msg;
	public ErrorResponse(String msg)
	{
		this.msg=msg;
	}

	@Override
	public void run(GameObserver o) {
		o.onError(msg);
	}

}
