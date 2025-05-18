package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.notificationimpl;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class NotificationJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        new NotificationHandler(getApplicationContext()).send("Tábordíj befizetési határidőd van 1 napon belül!");

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
