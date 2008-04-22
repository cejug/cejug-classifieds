package net.java.dev.cejug.classifieds.ws.orm;

import java.sql.SQLException;
import java.util.List;

import net.java.dev.cejug.classifieds.server.generated.contract.MonitorResponse;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorResponse.ResponseTime;

public class StatusCrud implements
		ClassifiedsCrud<MonitorResponse.ResponseTime> {

	@Override
	public ResponseTime create() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(ResponseTime type) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(ResponseTime type) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ResponseTime> read(int limit) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResponseTime> read(String query, int limit) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(ResponseTime type) throws SQLException {
		// TODO Auto-generated method stub

	}
}
