package com.mekanly.presentation.ui.fragments.hint

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mekanly.R
import com.mekanly.databinding.FragmentHintBinding

class HintFragment : Fragment() {

    private lateinit var binding : FragmentHintBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHintBinding.inflate(inflater, container, false)


        initListeners()


        val fullText = """
    <b>Mekanly.com</b> platformasynyň ähli mümkinçiliklerinden doly peýdalanmak üçin aşakdaky ädimleri ýerine ýetiriň:<br><br>

    <b>1.</b> Ilki bilen, programmanyň doly mümkinçiliklerini ulanmak üçin <b>'Ulgama gir'</b> düwmesine basyp telefon belgiňizi girizip, hasabyňyzy tassyklamaly.<br><br>

    <b>2.</b> Emläk gözlemek üçin filterleri ulanyp bilersiňiz. <b>'Filterler'</b> bölüminden baha aralygyny, ýerleşýän ýerini, meýdany we beýleki aýratynlyklary saýlap bilersiňiz.<br><br>

    <b>3.</b> Islendik emlägiň sahypasyna girip, onuň jikme-jik aýratynlyklaryny we suratlaryny görüp bilersiňiz. <b>'Habarlaşmak'</b> düwmesi bilen emläk eýesine, agentine jaň edip ýa-da habar iberip bilersiňiz.<br><br>

    <b>4.</b> Emläk bildirişini goşmak. Eger siz öz emlägiňizi satmak ýa-da kärendesine bermek isleýän bolsaňyz, <b>'Bildiriş goşmak'</b> bölümine girip, gerekli maglumatlary girizmeli. Suratlary we bahany girizip, bildirişiňizi tiz we aňsat ýerleşdirip bilersiňiz.<br><br>

    <b>5.</b> Bildiriş goşaňyzda üns bermeli zatlar:<br>
    - Suratlaryň sany <b>3-den köp</b> bolmaly we hili gowy bolmaly<br>
    - Emlägiň ýerleşýän ýeri we girizilýän maglumatlary takyk<br>
    - Eger-de siziň isleýän mümkinçiligiňiz ýok bolsa, onda <b>admin</b> bilen habarlaşyp goşduryp bilersiňiz<br>
    - Emlägiňizi <b>VIP</b> we <b>LUXE</b> derejeli görkezmek, onuň <b>çalt satylmagyna</b> kömek eder
""".trimIndent()

        binding.instructionTextView.text = Html.fromHtml(fullText, Html.FROM_HTML_MODE_LEGACY)





        return binding.root
    }

    private fun initListeners() {

        binding.apply {

            backBtn.setOnClickListener{
                findNavController().popBackStack()
            }



        }


    }


}