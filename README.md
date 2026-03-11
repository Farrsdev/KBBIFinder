# KBBI Finder 📚

**KBBI Finder** adalah aplikasi Android untuk mencari kata dalam dataset KBBI secara cepat dan offline.
Aplikasi ini dibuat menggunakan **Kotlin** dan **Jetpack Compose** dengan tujuan mengeksplorasi berbagai teknik pencarian kata seperti **prefix**, **suffix**, dan filter khusus.

Repository pada branch ini berisi **source code aplikasi**.

---

# ✨ Fitur

### 🔎 Pencarian Prefix

Mencari kata yang **diawali** dengan teks tertentu.

Contoh:

```
query: pr
hasil: pria, proses, proyek, produk
```

---

### 🔚 Pencarian Suffix

Mencari kata yang **diakhiri** dengan teks tertentu.

Contoh:

```
query: an
hasil: makanan, jalan, tulisan
```

---

### 🔤 Mode Tanpa Vokal

Kata yang **tidak memiliki huruf vokal (a, i, u, e, o)** akan:

* diprioritaskan di bagian atas
* ditampilkan dengan highlight khusus

---

### 🔃 Pengurutan Hasil

Hasil pencarian dapat diurutkan:

* **A-Z (Ascending)**
* **Z-A (Descending)**

---

### 🎨 Pemilihan Warna Tema

Pengguna dapat mengganti warna aksen aplikasi secara dinamis.

Tema yang tersedia:

* Green
* Blue
* Purple
* Orange
* Teal

---

### 💡 Rekomendasi Pencarian

Aplikasi menyediakan chip rekomendasi untuk mencoba beberapa awalan kata populer seperti:

```
ax
ex
y
v
pr
st
in
co
```

---

### ⚡ Pencarian Offline

Dataset kata dimuat dari file lokal sehingga:

* tidak memerlukan koneksi internet
* pencarian berlangsung sangat cepat

---

# 🛠 Teknologi yang Digunakan

* **Kotlin**
* **Jetpack Compose**
* **Material 3**
* **Android Studio**

---

# 📂 Struktur Proyek

```
app/
 ├── src/main
 │   ├── java/com/example/kbbifinder
 │   │   └── MainActivity.kt
 │   ├── assets
 │   │   └── kbbi.txt
 │   └── res
```

---

# ⚙ Cara Kerja

1. Dataset dimuat dari file:

```
assets/kbbi.txt
```

2. Semua kata dinormalisasi menjadi huruf kecil.

3. Saat pengguna melakukan pencarian:

* Mode **Prefix** menggunakan `startsWith()`
* Mode **Suffix** menggunakan `endsWith()`

4. Jika **No Vowel Mode** aktif, kata tanpa vokal akan diprioritaskan di bagian atas.

5. Hasil pencarian dapat diurutkan secara **ascending** atau **descending**.

---

# 🚀 Menjalankan Project

1. Clone repository

```
git clone https://github.com/farrsdev/KBBIFinder.git
```

2. Buka project menggunakan **Android Studio**

3. Jalankan aplikasi pada emulator atau perangkat Android.

---

# 📊 Dataset

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

# 🔮 Pengembangan Selanjutnya

Beberapa ide pengembangan yang bisa ditambahkan:

* Menambahkan **arti kata**
* Menggunakan **SQLite / Full Text Search (FTS)** untuk dataset yang lebih besar
* Fitur **bookmark kata**
* Fitur **riwayat pencarian**
* Tampilan **dark / light theme**

---

# 👨‍💻 Pembuat

Farr

---

# 📜 Lisensi

Proyek ini bersifat open-source dan dibuat untuk keperluan pembelajaran serta eksplorasi pengembangan aplikasi Android.
