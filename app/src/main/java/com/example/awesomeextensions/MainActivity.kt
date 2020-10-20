package com.example.awesomeextensions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val images = listOf<String>(
            "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/10e1ce22-cd06-4069-972f-e5983cc638ee/d8sciq6-85f5e900-a187-45db-bc9a-d4d364242c01.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOiIsImlzcyI6InVybjphcHA6Iiwib2JqIjpbW3sicGF0aCI6IlwvZlwvMTBlMWNlMjItY2QwNi00MDY5LTk3MmYtZTU5ODNjYzYzOGVlXC9kOHNjaXE2LTg1ZjVlOTAwLWExODctNDVkYi1iYzlhLWQ0ZDM2NDI0MmMwMS5qcGcifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6ZmlsZS5kb3dubG9hZCJdfQ.pybj7f4cZzYMPKlMZycBx6XSe2JbNOuIkkJv5dGpuxE",
            "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/8359efd9-fa97-4919-8f02-3712e70a0eeb/de0tmf3-07d0190e-3bcc-454c-bf7d-940a126af2d8.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOiIsImlzcyI6InVybjphcHA6Iiwib2JqIjpbW3sicGF0aCI6IlwvZlwvODM1OWVmZDktZmE5Ny00OTE5LThmMDItMzcxMmU3MGEwZWViXC9kZTB0bWYzLTA3ZDAxOTBlLTNiY2MtNDU0Yy1iZjdkLTk0MGExMjZhZjJkOC5qcGcifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6ZmlsZS5kb3dubG9hZCJdfQ.KD82TlBAff__w6hSdIxcOU7byY3Vj5yurOiw1kEqvRs",
            "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/090a4f83-b48f-463e-bd78-23d310d495ab/d84tjgn-42cb8faf-0dde-45c6-9da0-d929ea4a8b90.jpg/v1/fill/w_1024,h_1449,q_75,strp/thor___worthy__digital_painting__by_unicatstudio_d84tjgn-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOiIsImlzcyI6InVybjphcHA6Iiwib2JqIjpbW3siaGVpZ2h0IjoiPD0xNDQ5IiwicGF0aCI6IlwvZlwvMDkwYTRmODMtYjQ4Zi00NjNlLWJkNzgtMjNkMzEwZDQ5NWFiXC9kODR0amduLTQyY2I4ZmFmLTBkZGUtNDVjNi05ZGEwLWQ5MjllYTRhOGI5MC5qcGciLCJ3aWR0aCI6Ijw9MTAyNCJ9XV0sImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS5vcGVyYXRpb25zIl19.J18lrgMbNZgebj6nwqeZT39OHr3yGoPAnMerWiBTBzI",
            "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/a372c840-1810-40a4-b67e-0281d6f08f02/dbf1omq-d77fda13-1816-4629-a7b8-56fdc2a2d737.jpg/v1/fill/w_1024,h_1449,q_75,strp/spiderman___homecoming_by_giadina96_dbf1omq-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOiIsImlzcyI6InVybjphcHA6Iiwib2JqIjpbW3sicGF0aCI6IlwvZlwvYTM3MmM4NDAtMTgxMC00MGE0LWI2N2UtMDI4MWQ2ZjA4ZjAyXC9kYmYxb21xLWQ3N2ZkYTEzLTE4MTYtNDYyOS1hN2I4LTU2ZmRjMmEyZDczNy5qcGciLCJoZWlnaHQiOiI8PTE0NDkiLCJ3aWR0aCI6Ijw9MTAyNCJ9XV0sImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS53YXRlcm1hcmsiXSwid21rIjp7InBhdGgiOiJcL3dtXC9hMzcyYzg0MC0xODEwLTQwYTQtYjY3ZS0wMjgxZDZmMDhmMDJcL2dpYWRpbmE5Ni00LnBuZyIsIm9wYWNpdHkiOjk1LCJwcm9wb3J0aW9ucyI6MC40NSwiZ3Jhdml0eSI6ImNlbnRlciJ9fQ.BfraVjjw8W9SdY2sLC-2zpIUth0TlyjKdy2HO1ZXuhw"
        )

        btn.setOnClickListener {
            val randomIndex = (0..3).random()
            val url = images.get(randomIndex)
            image.loadImage(
                url, PicassoOption(
                    enableDebug = true,
                    errorHolder = R.drawable.ic_launcher_foreground,
                    placeholder = R.drawable.ic_launcher_background,
                    scaleType = PicassoScaleType.CenterCrop,
                    cache = PicassoCache.ForceLRU
                )
            )
        }

// todo dsl???
//        image {
//            url = ""
//            errorHolder =""
//            dasd = ""
//        }

    }
}

