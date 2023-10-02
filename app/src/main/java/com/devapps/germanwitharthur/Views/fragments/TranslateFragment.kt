package com.devapps.germanwitharthur.Views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.devapps.germanwitharthur.R
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class TranslateFragment : Fragment() {

    //lists of language names and codes
    private val languageNames = listOf<String>("English", "German")
    private val languageCodes = listOf<String>("en", "de")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sourceLanguageSpinner = view.findViewById<Spinner>(R.id.sourceLanguage)
        val targetLanguageSpinner = view.findViewById<Spinner>(R.id.targetLanguage)

        val languageAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item, languageNames)
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sourceLanguageSpinner.adapter = languageAdapter
        targetLanguageSpinner.adapter = languageAdapter

        val translateButton = view.findViewById<Button>(R.id.translate)
        val englishInput = view.findViewById<EditText>(R.id.englishText)
        val germanTranslation = view.findViewById<TextView>(R.id.translatedText)

        translateButton.setOnClickListener {
            val inputText = englishInput.text.toString()

            // Get the selected source and target language codes
            val sourceLanguageCode = languageCodes[sourceLanguageSpinner.selectedItemPosition]
            val targetLanguageCode = languageCodes[targetLanguageSpinner.selectedItemPosition]

            if (inputText.isNotEmpty()) {
                translateText(inputText, sourceLanguageCode, targetLanguageCode, germanTranslation)
            } else {
                Toast.makeText(requireContext(), "Enter text to translate", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun translateText(inputText: String, sourceLanguageCode: String,
                              targetLanguageCode: String, translatedTextView: TextView) {

        //English German translation object
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguageCode)
            .setTargetLanguage(targetLanguageCode)
            .build()

        // Create a translator
        val englishGermanTranslator = Translation.getClient(options)
        var conditions = DownloadConditions.Builder()
            .build()
        englishGermanTranslator.downloadModelIfNeeded().addOnSuccessListener {
            englishGermanTranslator.translate(inputText)
                .addOnSuccessListener { translatedText ->
                    translatedTextView.text = translatedText
                }
                .addOnFailureListener {exception ->
                    Toast.makeText(requireContext(), "Translation failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
