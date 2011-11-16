package jobs;

import models.account.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@SuppressWarnings("rawtypes")
@OnApplicationStart
public class Startup extends Job{
	
	public void doJob() throws Exception{
		if (User.count()==0){
			Fixtures.loadModels("initial-data.yml");
		}
	}
	
}
