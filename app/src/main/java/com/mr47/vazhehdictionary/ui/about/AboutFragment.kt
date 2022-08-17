package com.mr47.vazhehdictionary.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mr47.vazhehdictionary.R
import com.mr47.vazhehdictionary.databinding.FragmentAboutBinding

import androidx.core.text.HtmlCompat




class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val htmlString = "<p>این دیکشنری شامل واژگان تخصصی 38 رشته تخصصی هست.</p>\n" +
                "\n" +
                "<p>برای پیدا کردن معنی هر واژه، با وارد کردن عبارت فارسی یا انگلیسی مدنظر در قسمت جستجو ترجمه آن نمایش داده می شود.</p><br/>\n" +
                "\n" +
                "<p>این دیکشنری به صورت متن باز توسعه داده شده و عوامل ساخت این برنامه هیچ حق انحصاری روی کد و بانک اطلاعاتی این نرم افزار که با زحمت فراوان جمع آوری شده نداریم و تمامی اطلاعات موجود از منابع آزاد در اینترنت جمع آوری شده اند. </p>\n" +
                "\n" +
                "<p><span style=\"font-family:Times New Roman,Times,serif\">عزیزانی که قصد همراهی برای توسعه و اضافه کردن امکانات بیشتر به این واژه نامه را دارند از طریق گیت هاب پروژه میتوانند تغییراتشان را اعمال کنند و برای ادغام کردن با این پروژه ارسال کنند.</span></p>\n" +
                "\n" +
                "<p><a href=\"https://github.com/PayamDarabi/vazhehdictionary\">آدرس گیت هاب پروژه</a></p>\n" +
                "\n" +
                "<br/><p>با تشکر از عوامل همراه در ساخت این اپلیکیشن</p>\n" +
                "\n" +
                "<p><span style=\"font-family:Times New Roman,Times,serif\"><a href=\"https://www.linkedin.com/in/payam-darabi-162a58108\">پیام دارابی</a></span></p>\n" +
                "\n" +
                "<p><span style=\"font-family:Times New Roman,Times,serif\"><a href=\"https://www.linkedin.com/in/pouyadarabi\">پویا دارابی</a></span></p>\n" +
                "\n" +
                "<p><span style=\"font-family:Times New Roman,Times,serif\"><a href=\"https://www.linkedin.com/in/paria-moarref-16a317123\">پریا معرف</a></span></p>\n" +
                "\n" +
                "\n" +
                "<br/><p>منابع:</p>\n" +
                "\n" +
                "<p><a href=\"https://cafebazaar.ir/app/com.managmentdic\">دیکشنری تخصصی مدیریت</a></p>\n" +
                "\n" +
                "<p><a href=\"https://www.ir-translate.com/pu/Dictionary/\">دیکشنری البرز</a></p>"

        val spanned = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_COMPACT)

        val tvOutput = root.findViewById(R.id.text_about) as TextView

        tvOutput.setMovementMethod(LinkMovementMethod.getInstance());
        tvOutput.text = spanned

        val btnRate=root.findViewById<Button>(R.id.btnRate)
        btnRate.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_EDIT)
            intent.data = Uri.parse("bazaar://details?id=com.mr47.vazhehdictionary")
            intent.setPackage("com.farsitel.bazaar")
            startActivity(intent)
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}