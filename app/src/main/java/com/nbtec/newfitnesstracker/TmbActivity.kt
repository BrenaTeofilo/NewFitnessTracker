package com.nbtec.newfitnesstracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.nbtec.newfitnesstracker.model.Calc

class TmbActivity : AppCompatActivity() {

    private lateinit var lifestyle: AutoCompleteTextView
    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var editAge: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)

        editWeight = findViewById(R.id.edit_tmb_weight)
        editHeight = findViewById(R.id.edit_tbm_height)
        editAge = findViewById(R.id.edit_tbm_age)

        lifestyle = findViewById(R.id.auto_lifestyle)
        val items = resources.getStringArray(R.array.tmb_lifestyle)
        lifestyle.setText(items.first())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lifestyle.setAdapter(adapter)

        val btnSend: Button = findViewById(R.id.btn_tmb_calc)
        btnSend.setOnClickListener {
            if (!validateTmb()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        val weight = editWeight.text.toString().toInt()
        val height = editHeight.text.toString().toInt()
        val age = editAge.text.toString().toInt()
        val result = calculateTmb(weight, height, age)
        val tmbResponse = tmbRequest(result)

        AlertDialog.Builder(this)
            .setMessage(getString(R.string.tmb_response, tmbResponse))
            .setPositiveButton(android.R.string.ok) { dialog, which -> }
            .setNegativeButton(R.string.save) { dialog, which ->
                Thread {
                    val app = application as App
                    val dao = app.db.calcDao()
                    dao.insert(Calc(type = "tmb", res = tmbResponse))

                    runOnUiThread {
                        openListActivity()
                    }
                }.start()
            }
            .create()
            .show()

        val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_search) {
            finish()
            openListActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openListActivity() {
        val intent = Intent(this, ListCalcActivity::class.java)
        intent.putExtra("type", "tmb")
        startActivity(intent)
    }

    private fun tmbRequest(tmb: Double): Double {
        val items = resources.getStringArray(R.array.tmb_lifestyle)
        return when {
            lifestyle.text.toString() == items[0] -> tmb * 1.2
            lifestyle.text.toString() == items[1] -> tmb * 1.375
            lifestyle.text.toString() == items[2] -> tmb * 1.55
            lifestyle.text.toString() == items[3] -> tmb * 1.725
            lifestyle.text.toString() == items[4] -> tmb * 1.9
            else -> 0.0
        }
    }

    private fun calculateTmb(weight: Int, height: Int, age: Int): Double {
        return 66 + (13.7 * weight) + (5 * height) - (6.8 * age)
    }

    private fun validateTmb(): Boolean {
        return (editWeight.text.toString().isNotEmpty()
                && editHeight.text.toString().isNotEmpty()
                && editAge.text.toString().isNotEmpty()
                && !editWeight.text.toString().startsWith("0")
                && !editHeight.text.toString().startsWith("0")
                && !editAge.text.toString().startsWith("0"))
    }
}
//
//fun onRadioButtonClicked(view: View): Double {
//    if (view is RadioButton) {
//        // O botão está marcado agora?
//        val checked = view.isChecked
//
//        // Verifique qual botão de opção foi clicado
//        when (view.getId()) {
//            R.id.btn_masculine ->
//                if (checked) {
//                    // masculino
//                    return 66 + (13.7 * weight) + (5 * height) - (6.8 * age)
//                }
//            R.id.btn_feminine ->
//                //Fórmula para mulheres: TMB
//                if (checked) return 655 + (9.6 * weight) + (1.8 * height)
//        }
//    }
//}

//Fórmula para homens: TMB
//return 66 + (13.7 * weight) + (5 * height) - (6.8 * age)
//Fórmula para mulheres: TMB
//return 655 + (9.6 * weight) + (1.8 * height) – (4.7 * age)
