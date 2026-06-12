Film ve Dizi Kaşifi (Movie & TV Explorer)

Merhaba! Bu proje; modern Android dünyasını, güncel teknolojileri ve mimari standartları öğrenirken baştan sona kendim geliştirdiğim bir sinema rehberi uygulamasıdır.

Uygulama, TMDB API kullanarak en popüler filmleri listeler ve arama yapmanıza olanak tanır. Karmaşık ve projeyi şişiren hazır kütüphanelere boğulmak yerine, problemleri temel algoritmalarla ve temiz kod yazarak çözmeyi hedefledim.
Farklı Dil (TR / EN / RU): Uygulama, telefonunuzun dilini otomatik algılıyor. Detaylar, özetler ve menüler anında bulunduğunuz dile adapte oluyor.

Gece Modu (Dark/Light Theme): Göz yormaması için profil ekranından kolayca değiştirilebilen, sisteme entegre tema desteği ekledim.

Kendi Yazdığım Sayfalama Mantığı: Arama sonuçlarında devasa Paging kütüphaneleri kullanmak yerine, Jetpack Compose'un yeteneklerini kullanarak sıfırdan basit ama tıkır tıkır çalışan bir sonsuz kaydırma (Pagination) algoritması yazdım.

Çökmeyen Uygulama: İnternetiniz giderse uygulama hata verip yüzünüze kapanmıyor; durumu yakalayıp kendi dilinizde kibarca bağlantınızı kontrol etmenizi istiyor.

Güvenilirlik: `utils` kısmında yazdığım puan formatlama gibi araçların doğruluğunu kanıtlamak için arka plana temel düzeyde Unit Test'ler ekledim.

Arkada Hangi Araçlar Çalışıyor?

Projeyi, sürdürülebilir olması adına MVVM mimarisiyle inşa ettim.
Arayüz: Jetpack Compose (Bütün tasarım bununla yapıldı)
Ağ İşlemleri: Retrofit2 & OkHttp
Bağımlılık Enjeksiyonu: Dagger Hilt
Asenkron Yapı: Kotlin Coroutines & StateFlow
Görseller: Coil

Nasıl Deneyebilirsiniz?
1. Projeyi bilgisayarınıza klonlayın.
2. Android Studio ile açın.
3. Uygulamanın verileri çekebilmesi için internet bağlantınızın açık olduğundan emin olup projeyi çalıştırın (Run).
   (Not: Sistemde kendi TMDB API Key'im bulunmaktadır, direkt test edebilirsiniz).

Vakit ayırıp incelediğiniz için şimdiden teşekkürler!

Uygulama Demo Videosu

[https://github.com/KaganAstro/FilmveDiziKasifi/blob/master/demo.mp4]

**Geliştirici:** Kağan Kulaoğlu
