https://developer.android.com/guide/topics/ui/notifiers/notifications


// Schedule at specific time (with repeating support)
    Date at = schedule.getAt();
    if (at != null) {
      if (at.getTime() < new Date().getTime()) {
        Log.e(LogUtils.getPluginTag("LN"), "Scheduled time must be *after* current time");
        return;
      }
      if (schedule.isRepeating()) {
        long interval = at.getTime() - new Date().getTime();
        alarmManager.setRepeating(AlarmManager.RTC, at.getTime(), interval, pendingIntent);
      } else {
        alarmManager.setExact(AlarmManager.RTC, at.getTime(), pendingIntent);
      }
      return;
    }

    // Schedule at specific intervals
    String every = schedule.getEvery();
    if (every != null) {
      Long everyInterval = schedule.getEveryInterval();
      if (everyInterval != null) {
        long startTime = new Date().getTime() + everyInterval;
        alarmManager.setRepeating(AlarmManager.RTC, startTime, everyInterval, pendingIntent);
      }
      return;
    }

    // Cron like scheduler
    DateMatch on = schedule.getOn();
    if (on != null) {
      notificationIntent.putExtra(TimedNotificationPublisher.CRON_KEY, on.toMatchString());
      pendingIntent = PendingIntent.getBroadcast(context, request.getId(), notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
      alarmManager.setExact(AlarmManager.RTC, on.nextTrigger(new Date()), pendingIntent);
    }
  }
