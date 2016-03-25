README
=======


### How would you implement a page which shows a list of contacts?

* Create an own database for it.

### Why would you ever use a set-up with virtual machines in a real (production) environment? Or would you not?

* We would not. VM's use a lot of space (for example, on oksa3, there could be only 2000 VM's), which is why in real environments they use super computers that go straight into the hardware.
  VM's are fast, though.

### Which of the optimizations made sense, which ones not?    

* With the cache:     summary = 500 in 662s = 0.8/s   Avg: 1317   Min: 791    Max: 1734   Err: 4 (0.80%)
* Without the cache:  summary = 500 in 668s = 0.7/s   Avg: 1329   Min: 1104   Max: 1501   Err: 1 (0.20%)

* Here we can see the one with the cache was a bit more efficient.

### Compute the daily cost of your amazon usage including      
                       
* Cost of running the VMs:     0.732 $


* We could not log into the AWS and do the chmod (public key) thing on neither our own comps nor the demo room's...
