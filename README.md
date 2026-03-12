# KBBI Finder (Web Version) 🌐

**KBBI Finder Web** adalah versi berbasis browser dari aplikasi **KBBI Finder**.
Aplikasi ini memungkinkan pengguna mencari kosakata Bahasa Indonesia secara cepat menggunakan dataset lokal KBBI langsung dari browser.

Versi ini dibuat untuk eksplorasi **pencarian kata berbasis web** dengan antarmuka ringan dan responsif.

---

# ✨ Fitur

### 🔎 Prefix Search

Mencari kata yang **diawali** dengan teks tertentu.

Contoh:

```
pr → pria, proses, proyek, produk
```

---

### 🔚 Suffix Search

Mencari kata yang **diakhiri** dengan teks tertentu.

Contoh:

```
an → makanan, tulisan, jalan
```

---

### 🔤 Mode Tanpa Vokal

Menyorot kata yang **tidak memiliki huruf vokal** *(a, i, u, e, o)*.

Contoh:

```
rhythm
brrr
shh
```

---

### ⚡ Pencarian Cepat

Dataset kata dimuat secara lokal sehingga pencarian dapat dilakukan **tanpa koneksi internet** setelah halaman dimuat.

---

### 💡 Rekomendasi Pencarian

Tersedia beberapa rekomendasi awalan kata untuk mencoba pencarian dengan cepat.

---

# 🛠 Teknologi

Versi web dibuat menggunakan teknologi dasar web:

* **HTML**
* **CSS**
* **JavaScript**

Tanpa framework besar agar tetap ringan dan mudah dipahami.

---

# 📂 Struktur Proyek

```
web/
 ├── index.html
 ├── style.css
 ├── script.js
 └── data
    └── kbbi.txt
```

---

# ⚙ Cara Menjalankan

1. Clone repository

```
git clone https://github.com/farrsdev/KBBIFinder.git
```

2. Masuk ke branch **web**

```
git checkout web
```

3. Buka file berikut di browser

```
index.html
```

atau jalankan menggunakan server lokal.

---

# 📊 Dataset

Dataset kata disimpan dalam file:

```
kbbi.txt
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

Beberapa ide pengembangan:

* Menambahkan **pencarian regex**
* Fitur **auto suggestion**
* Menampilkan **jumlah hasil per huruf**
* Menambahkan **mode wildcard search**

---

# 👨‍💻 Pembuat

Farr

---

# 📜 Lisensi

Proyek ini dibuat untuk keperluan pembelajaran dan eksplorasi pengembangan aplikasi.
