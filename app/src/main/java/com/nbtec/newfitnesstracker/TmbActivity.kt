package com.nbtec.newfitnesstracker

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class TmbActivity : AppCompatActivity() {

    private lateinit var lifestyle: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)

        lifestyle = findViewById(R.id.auto_lifestyle)
        val items = resources.getStringArray(R.array.tmb_lifestyle)
        lifestyle.setText(items.first())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lifestyle.setAdapter(adapter)
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // O botão está marcado agora?
            val checked = view.isChecked

            // Verifique qual botão de opção foi clicado
            when (view.getId()) {
                R.id.btn_masculine ->
                    if (checked) {
                        // masculino
                    }
                R.id.btn_feminine ->
                    if (checked) {
                        // feminino
                    }
            }
        }
    }
}

//Taxa de atividade:
//Sedentários (pouco ou nenhum exercício) fator = 1.2
//Levemente ativo (exercício leve 1 a 3 dias por semana) fator = 1.375
//Moderadamente ativo (exercício moderado, faz esportes 3 a 5 dias por semana) fator = 1.55
//Altamente ativo (exercício pesado de 5 a 6 dias por semana) fator = 1.725
//Extremamente ativo (exercício pesado diariamente e até 2 vezes por dia) fator = 1.9

//Fórmula para homens: TMB = fator da taxa de atividade x
//{66 + [(13,7 x Peso(kg)) + ( 5 x Altura(cm)) – (6,8 x Idade(anos))]}
//
//Fórmula para mulheres: TMB = fator da taxa de atividade x
//{655 + [(9,6 x Peso(kg)) + (1,8 x Altura(cm)) – (4,7 x Idade(anos))]}