# Test Detail
## User Action
1. Open App and click clear log and init billing service
2. Close App
3. wait for 5 mins and capture the logcat
> adb logcat -d | grep 'JobInfoScheduler
## Sample of the logcat result
````
5-27 15:43:51.551 11783 11972 D JobInfoScheduler: isFeatureSupported
05-27 15:45:09.709  2491  2652 I ActivityManager: Start proc 12731:com.bytedance.android.live.pipo.pbl6_test/u0a370 for service {com.bytedance.android.live.pipo.pbl6_test/com.google.android.datatransport.runtime.scheduling.jobscheduling.JobInfoSchedulerService}
05-27 15:45:09.923 12731 12731 D JobInfoScheduler: TestApplication: init
05-27 15:45:09.923 12731 12731 D JobInfoScheduler: TestApplication: attachBaseContext
05-27 15:45:09.923 12731 12731 D JobInfoScheduler: TestApplication: mainThread: true
05-27 15:45:09.923 12731 12731 D JobInfoScheduler: TestApplication: attachBaseContext com.bytedance.android.live.pipo.pbl6_test
05-27 15:45:09.941 12731 12731 D JobInfoScheduler: TestApplication: onCreate
````
### Explanation of log result

15:43 -> Billing Service ``isFeatureSupport`` is called, then app closed

15:45 -> App process ``pbl6_test`` is started by ``JobInfoSchedulerService``

15:45 -> Application is called and init




