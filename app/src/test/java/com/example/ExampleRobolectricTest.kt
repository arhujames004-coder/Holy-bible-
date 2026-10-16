package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("The Holy Bible", appName)
  }

  @Test
  fun `verify canonical bible books count`() {
    assertEquals(39, com.example.data.model.BibleCatalog.OLD_TESTAMENT.size)
    assertEquals(27, com.example.data.model.BibleCatalog.NEW_TESTAMENT.size)
    assertEquals(66, com.example.data.model.BibleCatalog.ALL_BOOKS.size)
  }
}
