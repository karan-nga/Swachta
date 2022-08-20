package com.rawtooth.swachta

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.rawtooth.swachta.databinding.ActivityMainPickupScheduledBinding
import com.rawtooth.swachta.schedule.FetchLocation

class MainPickupScheduled : AppCompatActivity(),AdapterView.OnItemSelectedListener {
    var cost = 0.0
    var already = ""
    var btProceed: Button? = null
    var etSum = 0
    var metal = ""
    var paper = ""
    var glass = ""
    var plastic = ""
    var rubber = ""
    var cardboard = ""
    var trash = ""
    var waste = ""
    private val TAG = "PickupActivity"
    lateinit var binding: ActivityMainPickupScheduledBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainPickupScheduledBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val spinner = binding.spinner

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.waste_array, android.R.layout.simple_spinner_item
        )


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        val selected = parent!!.getItemAtPosition(pos).toString()
        val etName = findViewById<View>(R.id.etName) as TextView
        val etPrice = findViewById<View>(R.id.etPrice) as TextView
        val medicinesalready = etName.text.toString()
        val costalready = etPrice.text.toString()
        if (selected == "Select Item") {
            //
        } else {
            if (selected == "Metal") {
                metal()
                if (!already.contains("1")) {
                    if (cost == 0.0) {
                        etName.text = "Metal"
                        etPrice.text = "₹33/kg"
                        already += "1"
                    } else {
                        etName.text = "$medicinesalready\nMetal"
                        etPrice.text = "$costalready\n₹33/kg"
                    }
                    cost += 33.0
                }
            } else if (selected == "Paper") {
                paper()
                if (!already.contains("2")) {
                    if (cost == 0.0) {
                        etName.text = "Paper"
                        etPrice.text = "₹11/kg"
                        already += "2"
                    } else {
                        etName.text = "$medicinesalready\nPaper"
                        etPrice.text = "$costalready\n₹11/kg"
                    }
                }
                cost += 11.0
            } else if (selected == "Glass") {
                glass()
                if (!already.contains("3")) {
                    if (cost == 0.0) {
                        etName.text = "Glass"
                        etPrice.text = "₹7/kg"
                        already += "3"
                    } else {
                        etName.text = "$medicinesalready\nGlass"
                        etPrice.text = "$costalready\n₹7/kg"
                    }
                }
                cost += 7.0
            } else if (selected == "Plastic") {
                plastic()
                if (!already.contains("4")) {
                    if (cost == 0.0) {
                        etName.text = "Plastic"
                        etPrice.text = "₹28/kg"
                        already += "4"
                    } else {
                        etName.text = "$medicinesalready\nPlastic"
                        etPrice.text = "$costalready\n₹28/kg"
                    }
                }
                cost += 28.0
            } else if (selected == "Rubber") {
                rubber()
                if (!already.contains("5")) {
                    if (cost == 0.0) {
                        etName.text = "Rubber"
                        etPrice.text = "₹6.5/kg"
                        already += "5"
                    } else {
                        etName.text = "$medicinesalready\nRubber"
                        etPrice.text = "$costalready\n₹6.5/kg"
                    }
                }
                cost += 6.5
            } else if (selected == "Cardboard") {
                cardboard()
                if (!already.contains("6")) {
                    if (cost == 0.0) {
                        etName.text = "Cardboard"
                        etPrice.text = "₹15/kg"
                        already += "6"
                    } else {
                        etName.text = "$medicinesalready\nCardboard"
                        etPrice.text = "$costalready\n₹15/kg"
                    }
                }
                cost += 15.0
            } else if (selected == "Trash") {
                trash()
                if (!already.contains("7")) {
                    if (cost == 0.0) {
                        etName.text = "Trash"
                        etPrice.text = "₹17.5/kg"
                        already += "7"
                    } else {
                        etName.text = "$medicinesalready\nTrash"
                        etPrice.text = "$costalready\n₹17.5/kg"
                    }
                }
                cost += 17.5
            }
        }
        // waste();
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {

    }
    private fun metal(): String? {
        metal = "Metal "
        return metal
    }

    private fun paper(): String? {
        paper = "Paper "
        return paper
    }

    private fun glass(): String? {
        glass = "Glass "
        return glass
    }

    private fun plastic(): String? {
        plastic = "Plastic "
        return plastic
    }

    private fun rubber(): String? {
        rubber = "Rubber "
        return rubber
    }

    private fun cardboard(): String? {
        cardboard = "Cardboard "
        return cardboard
    }

    private fun trash(): String? {
        trash = "Trash "
        return trash
    }

    private fun waste(): String? {
        waste = metal + paper + glass + plastic + rubber + cardboard + trash
        Log.d(TAG, "waste: " + metal + "end")
        return waste
    }

    fun goToProceed(view: View) {
        waste()
        val intent = Intent(this, FetchLocation::class.java)
        intent.putExtra("waste_materials", waste)
        val costStr = cost.toString()
        intent.putExtra("cost_of_waste", costStr)
        startActivity(intent)
    }
}