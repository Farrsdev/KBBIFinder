# KBBI Finder 📚

**KBBI Finder** adalah aplikasi Android untuk mencari kata dalam dataset KBBI secara cepat dan offline.
Aplikasi ini dibuat menggunakan **Kotlin** dan **Jetpack Compose** dengan fokus pada eksplorasi kosakata Bahasa Indonesia melalui berbagai mode pencarian.

---

# 📦 Download APK

File **APK** tersedia di repository ini.

Silakan unduh dan instal di perangkat Android untuk mencoba aplikasinya.

---

# ⚠ Struktur Branch Repository

Repository ini menggunakan dua branch utama:

**master**
Berisi **source code aplikasi**.

**main**
Berisi **informasi repository dan file rilis (APK)**.

Jika ingin melihat atau memodifikasi kode sumber, silakan clone branch `master`.

---

# ⬇ Clone Source Code

Clone langsung branch `master`:

```bash
git clone -b master https://github.com/farrsdev/KBBIFinder.git
```

atau:

```bash
git clone https://github.com/farrsdev/KBBIFinder.git
git checkout master
```

---

# ✨ Fitur Aplikasi

### 🔎 Prefix Search

Mencari kata yang **diawali** dengan teks tertentu.

Contoh:

```
query: pr
hasil: pria, proses, proyek, produk
```

---

### 🔚 Suffix Search

Mencari kata yang **diakhiri** dengan teks tertentu.

Contoh:

```
query: an
hasil: makanan, jalan, tulisan
```

Pada mode ini hasil juga **dikelompokkan berdasarkan huruf terakhir** untuk memudahkan eksplorasi.

---

### 🔤 Mode Tanpa Vokal (No Vowel Mode)

Kata yang **tidak memiliki huruf vokal (a, i, u, e, o)** akan:

* diprioritaskan di bagian atas
* ditampilkan dengan highlight khusus

Contoh kata:

```
rhythm
brrr
shh
```

---

### 🔃 Sorting

Hasil pencarian dapat diurutkan:

* **A-Z (Ascending)**
* **Z-A (Descending)**

---

### 🎨 Theme Color Picker

Pengguna dapat mengganti warna aksen aplikasi:

* Green
* Blue
* Purple
* Orange
* Teal

Perubahan warna diterapkan secara dinamis pada UI.

---

### 💡 Rekomendasi Pencarian

Aplikasi menyediakan **chip rekomendasi** untuk mencoba awalan kata populer seperti:

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

Ini membantu pengguna langsung mencoba pencarian tanpa mengetik.

---

### ⚡ Pencarian Cepat Offline

Dataset dimuat dari file lokal sehingga:

* tidak memerlukan internet
* pencarian sangat cepat

---

# 🛠 Teknologi yang Digunakan

* **Kotlin**
* **Jetpack Compose**
* **Material 3**
* **Android Studio**

---

# 📂 Dataset

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

Dataset dimuat saat aplikasi dijalankan dan difilter secara lokal.

---

# 📊 Cara Kerja Pencarian

1. Dataset dimuat dari `assets/kbbi.txt`
2. Semua kata dinormalisasi menjadi huruf kecil
3. Kata difilter berdasarkan:

* `startsWith()` untuk prefix
* `endsWith()` untuk suffix

4. Hasil dapat diurutkan dan difilter tambahan seperti **No Vowel Mode**

---

# 👨‍💻 Pembuat

Farr

---

# 📜 Lisensi

Proyek ini dibuat untuk keperluan pembelajaran dan eksplorasi pengembangan aplikasi Android.
