# KBBI Finder 📚

**KBBI Finder** adalah aplikasi Android sederhana untuk mencari kata dalam daftar kosakata Bahasa Indonesia menggunakan dataset KBBI.
Aplikasi ini dibuat menggunakan **Kotlin** dan **Jetpack Compose** dengan fokus pada pencarian kata yang cepat dan fleksibel.

---

## ✨ Fitur

* 🔎 **Pencarian Prefix**
  Mencari kata yang **diawali** dengan teks tertentu.

* 🔚 **Pencarian Suffix**
  Mencari kata yang **diakhiri** dengan teks tertentu.

* 🔤 **Mode Tanpa Vokal**
  Menyorot kata yang **tidak memiliki huruf vokal (a, i, u, e, o)**.

* 🎨 **Pemilihan Warna Tema**
  Pengguna dapat mengganti warna aksen aplikasi.

* ⚡ **Pencarian Cepat**
  Menggunakan dataset lokal sehingga pencarian sangat cepat tanpa internet.

* 💡 **Rekomendasi Awalan Kata**
  Tombol cepat untuk mencoba beberapa awalan kata umum.

---

## 🛠 Teknologi yang Digunakan

* **Kotlin**
* **Jetpack Compose**
* **Material 3**
* **Android Studio**

---

## 📂 Struktur Proyek

```
app/
 ├── src/main
 │   ├── java/com/example/kbbifinder
 │   │   ├── MainActivity.kt
 │   │   └── DictionaryScreen.kt
 │   ├── assets
 │   │   └── kbbi.txt
 │   └── res
```

---

## ⚙ Cara Kerja

1. Aplikasi memuat daftar kata dari file `kbbi.txt` yang berada di folder **assets**.
2. Kata-kata tersebut disimpan di memori saat aplikasi dijalankan.
3. Saat pengguna mengetik pada kolom pencarian:

   * Mode **Prefix** menggunakan `startsWith()`
   * Mode **Suffix** menggunakan `endsWith()`
4. Jika mode **No Vokal** aktif, kata tanpa vokal akan ditampilkan dengan highlight khusus.

---

## 🚀 Cara Menjalankan

1. Clone repository ini

```
git clone https://github.com/username/kbbifinder.git
```

2. Buka project di **Android Studio**

3. Jalankan aplikasi di emulator atau perangkat Android.

---

## 📊 Dataset

Dataset kata disimpan pada file:

```
assets/kbbi.txt
```

Setiap baris berisi satu kata.

Contoh:

```
apel
api
ayam
buku
cinta
```

---

## 🔮 Pengembangan Selanjutnya

Beberapa ide pengembangan:

* Menambahkan **arti kata**
* Menggunakan **database SQLite atau FTS** untuk pencarian lebih cepat
* Fitur **bookmark kata**
* Fitur **riwayat pencarian**
* Mode **dark/light theme**

---

## 👨‍💻 Pembuat

Farr

---

## 📜 Lisensi

Proyek ini bersifat open-source dan dapat digunakan untuk keperluan pembelajaran.
