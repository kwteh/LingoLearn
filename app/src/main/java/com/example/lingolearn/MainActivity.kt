package com.example.lingolearn

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lingolearn.databinding.ActivityMainBinding
import com.example.lingolearn.ui.login.LoginActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "You can contact us at support@lingolearn.com", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        auth = FirebaseAuth.getInstance()

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val headerView = navView.getHeaderView(0)

        val headerUsernameTxt: TextView = headerView.findViewById(R.id.headerUsername)
        val headerEmailText: TextView = headerView.findViewById(R.id.headerEmail)
        val currUserUid = auth.currentUser!!.uid.toString()
        var userIsAdmin = false

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_account, R.id.nav_quiz, R.id.nav_aboutus, R.id.nav_product, R.id.nav_vocabulary, R.id.nav_quiz_management, R.id.nav_profile_management, R.id.nav_vocabulary_management, R.id.nav_payment_history
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        database = FirebaseDatabase.getInstance().getReference("User")
        database.child(currUserUid).get().addOnSuccessListener {
            val username = it.child("userName").value.toString()
            val email = it.child("email").value.toString()

            headerUsernameTxt.text = username
            headerEmailText.text = email

            if (it.child("role").value.toString() == "Admin") userIsAdmin = true

            navView.menu.findItem(R.id.nav_quiz_management).isVisible = userIsAdmin
            navView.menu.findItem(R.id.nav_profile_management).isVisible = userIsAdmin
            navView.menu.findItem(R.id.nav_vocabulary_management).isVisible = userIsAdmin

        }.addOnFailureListener {
            Toast.makeText(this, "Username doesn't exist!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                showAlertDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Do you want to logout?")

        builder.setPositiveButton("Yes") {dialog, which ->
            auth.signOut()
            Toast.makeText(this, "Successfully Logout", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        builder.setNegativeButton("No") {dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}