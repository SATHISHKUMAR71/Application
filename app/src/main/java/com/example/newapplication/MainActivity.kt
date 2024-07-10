package com.example.newapplication

import android.Manifest
import android.content.ContentUris
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract.Contacts
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity()
{
    val contactList = mutableListOf<com.example.newapplication.Contacts>()
    private val PERMISSION_CONTACTS = Manifest.permission.READ_CONTACTS
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val PERMISSION_READ_CALL_LOG = Manifest.permission.READ_MEDIA_IMAGES
    private lateinit var viewPager:ViewPager2
    private lateinit var viewPagerAdapter:FragmentViewPager
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.navView)
        val toolbar = findViewById<AppBarLayout>(R.id.appbar).findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(navView)
        }
        navView.setNavigationItemSelectedListener {
            it.isChecked = true
            drawerLayout.closeDrawers()
            true
        }
        viewPager = findViewById(R.id.fragmentViewPager)
        viewPagerAdapter = FragmentViewPager(this)
        viewPagerAdapter.addFragment(HomeFragment())
        viewPagerAdapter.addFragment(SecondFragment())
        viewPager.adapter = viewPagerAdapter
        val tabLayoutTitles = arrayOf("Home","Settings")
        val tabIcons = arrayOf(R.drawable.baseline_home_24,R.drawable.baseline_settings_24)
        TabLayoutMediator(findViewById(R.id.tabLayout),viewPager){tab,pos ->
            tab.text = tabLayoutTitles[pos]
            tab.setContentDescription(tabLayoutTitles[pos])
            tab.setIcon(tabIcons[pos])
        }.attach()


    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermission(){

        if((checkSelfPermission(PERMISSION_READ_CALL_LOG)== PackageManager.PERMISSION_GRANTED)&&(checkSelfPermission(PERMISSION_CONTACTS)== PackageManager.PERMISSION_GRANTED)){
            fetchContacts()
            println("Permission Granted")
        }
        else{
            AlertDialog.Builder(this).apply {
                setTitle("Permission Required")
                setCancelable(false)
                setMessage("This app needs to Read Call Logs")
                setPositiveButton("Grant Permission",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(PERMISSION_READ_CALL_LOG),10)
                    dialogInterface.dismiss()
                })
                setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                show()
            }
            AlertDialog.Builder(this).apply {
                setTitle("Permission Required")
                setCancelable(false)
                setMessage("This app needs to read contacts")
                setPositiveButton("Grant Permission",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(PERMISSION_CONTACTS),100)
                        dialogInterface.dismiss()
                    })
                setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                show()
            }
        }
    }

    private fun fetchContacts(){
        val sortOrder = "${Contacts.DISPLAY_NAME} DESC"
        val mediaSelection = "${Media._ID} DESC"
        val projections = arrayOf(
            Contacts._ID,
            Contacts.DISPLAY_NAME
        )
        val mediaProjections = arrayOf(
            Media.DATE_TAKEN,
            Media.DISPLAY_NAME,
            Media.SIZE
        )

        contentResolver.query(
            Media.INTERNAL_CONTENT_URI,
            mediaProjections,
            null,
            null,
            mediaSelection
        ).use {
            val date = it?.getColumnIndex(Media.DATE_TAKEN)
            val name = it?.getColumnIndex(Media.DISPLAY_NAME,)
            val size = it?.getColumnIndex(Media.SIZE)
            if(it != null){
                while (it.moveToNext()){
                    val mediaDate = it.getString(date!!)
                    val mediaName = it.getString(name!!)
                    val mediaSize = it.getString(size!!)
                    println("MediaDate: $mediaDate, MediaSize: $mediaSize, Media Name: $mediaName")
                }
            }
        }


        contentResolver.query(
            Contacts.CONTENT_URI,
            projections,
            null,
            null,
            sortOrder
        ). use { cursor ->
            val contactID = cursor?.getColumnIndex(Contacts._ID)
            val contactName = cursor?.getColumnIndex(Contacts.DISPLAY_NAME)
            if (cursor != null) {
                while (cursor.moveToNext()){
                    val id = cursor.getLong(contactID!!)
                    val name = cursor.getString(contactName!!)
                    val uri = ContentUris.withAppendedId(
                        Contacts.CONTENT_URI,id
                    )
                    println("Id: $id, Name: $name")
                    contactList.add(com.example.newapplication.Contacts(id,name,uri))
                }
            }
        }
    }
}