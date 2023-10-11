package com.a961084.ningrum.compose.kalkulatortip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a961084.ningrum.compose.kalkulatortip.ui.theme.KalkulatorTipTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KalkulatorTipTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    KalkulatorTipLayout()
                }
            }
        }
    }
}


@Composable
fun KalkulatorTipLayout() {
    var jumlah by remember { mutableStateOf("") }
    var tipMasuk by remember { mutableStateOf("") }

    val total= jumlah.toDoubleOrNull() ?: 0.0
    val tipPersen = tipMasuk.toDoubleOrNull() ?: 0.0
    val tip = hitungTip(tipPersen, total)

    Column(
        modifier = Modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.hitung_tip),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )
        EditFieldJumlah(
            label = R.string.jumlah_tagihan,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            nilai = jumlah,
            onNilaiBerubah = {jumlah = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        EditFieldJumlah(
            label = R.string.persentase_tip,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            nilai = tipMasuk,
            onNilaiBerubah = { tipMasuk = it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.jumlah_tip, tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

private fun hitungTip(jumlah: Double, tipPersen: Double = 15.0): String {
    val tip = tipPersen / 100 * jumlah
    return NumberFormat.getNumberInstance().format(tip)
}

@Preview(showBackground = true)
@Composable
fun KalkulatorTipLayoutPreview() {
    KalkulatorTipTheme {
        KalkulatorTipLayout()
    }
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
private fun EditFieldJumlah(
    @StringRes label : Int,
    keyboardOptions : KeyboardOptions,
    nilai : String,
    onNilaiBerubah : (String) -> Unit,
    modifier: Modifier = Modifier) {

    TextField(
        value = nilai,
        onValueChange = onNilaiBerubah,
        singleLine = true,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

