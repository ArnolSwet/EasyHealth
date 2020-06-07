package com.example.appEasyHealth

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.easyhealth.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class AddFood : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var client: Client
    private lateinit var txtName: TextInputEditText
    private lateinit var txtCalories: TextInputEditText
    private lateinit var typeMeal: String
    private lateinit var date: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("User")
        auth = FirebaseAuth.getInstance()

        txtName = findViewById(R.id.nameEdBreakMeal)
        txtCalories = findViewById(R.id.calEdBreakMeal)

        val spinner = findViewById<View>(R.id.spinLunch) as Spinner
        typeMeal = spinner.selectedItem.toString()

        val image: ImageView = findViewById<View>(R.id.imageTypeMeal) as ImageView
        val img_bfst: Int = R.drawable.breakfast
        val img_lun: Int = R.drawable.lunch
        val img_din: Int = R.drawable.dinner
        val img_sna: Int = R.drawable.snack
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    image.setImageResource(img_bfst)
                    typeMeal = spinner.selectedItem.toString()
                }
                if (position == 1) {
                    image.setImageResource(img_lun)
                    typeMeal = spinner.selectedItem.toString()
                }
                if (position == 2) {
                    image.setImageResource(img_din)
                    typeMeal = spinner.selectedItem.toString()
                }
                if (position == 3) {
                    image.setImageResource(img_sna)
                    typeMeal = spinner.selectedItem.toString()
                }
            }

        }

        date = getIntent().getSerializableExtra("Date").toString()

    }

    fun addFood( view: View) {

        val user: FirebaseUser? = auth.currentUser
        val userDB = databaseReference.child(user?.uid!!)

        userDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()

            }

            override fun onDataChange(p0: DataSnapshot) {
                client  = p0.getValue(Client::class.java)!!
                if (txtName.text.toString().isEmpty() || txtCalories.text.toString().isEmpty()) {
                    Toast.makeText(applicationContext,"ERROR: Empty fields", Toast.LENGTH_SHORT).show()
                } else {
                    var meal = Food(txtName.text.toString(), txtCalories.text.toString()+" kcal", date, typeMeal)
                    client.addFood(meal)
                    userDB.setValue(client)
                    Toast.makeText(applicationContext, "Food added correctly", Toast.LENGTH_SHORT).show()
                    goback(view)
                }
            }
        })

    }

    fun goback(view: View) {
        finish()
    }
}
