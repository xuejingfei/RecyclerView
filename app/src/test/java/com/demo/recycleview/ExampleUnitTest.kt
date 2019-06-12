package com.demo.recycleview

import org.junit.Test
import java.lang.Thread.sleep

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val lockObject = LockObject()
        Thread(Runnable {
            sleep(1000)
            lockObject.lock()
        }).start()
        Thread(Runnable {
            lockObject.lock1()
        }).start()
    }

    class LockObject {
        val rightLock: String = "yyy"
        val leftLock: String = "xxx"

        public fun lock() {
            synchronized(rightLock) {
                print("yyy-right got\n")
                synchronized(leftLock) {
                    print("xxx-left got\n")
                    print(rightLock + leftLock)
                }
            }

        }

        public fun lock1() {
            synchronized(leftLock) {
                synchronized(rightLock) {
                    print( leftLock+rightLock)
                }
            }

        }


    }
}
