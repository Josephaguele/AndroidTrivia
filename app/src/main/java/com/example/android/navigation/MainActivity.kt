/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        // initialize drawerLyout form the binding variable
        drawerLayout = binding.drawerLayout

        // This finds our navigationController from myNavHostFragment
        val navController = this.findNavController(R.id.myNavHostFragment)
        // This links the NavController to our Action Bar
        // drawerLayout is also added as the third parameter to setupActionBarWithNavController
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

      // create an appBarconfiguration with the navController.graph and drawerLayout
      appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

      // Hook up the navigation UI up to the navigation view
      NavigationUI.setupWithNavController(binding.navView, navController)

        /* We can prevent the drawer from being swiped anywhere other than the startDestination. All
         we need to do is call addOnDestinationChangedListener with a lambda that sets the
         DrawerLockMode depending on what destination we're navigating to. When the id of our
         NavDestination matches the startDestination of our graph, we'll unlock the drawerLayout;
         otherwise, we'll lock and close the drawerLayout*/
        navController.addOnDestinationChangedListener{ nc: NavController, nd: NavDestination, args:Bundle? ->
            if (nd.id == nc.graph.startDestination)  // if the navigated destination, is the start page
            { // then unlock the DrawerLayout
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else{ // if the navigated destination, is not the start page, then lock the DrawerLayout
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

    }

    /*We need to use NavigationUI.navigateUp with the drawerLayout as a parameter instead
    of navController.navigateUp */
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController,drawerLayout)
    }




}
