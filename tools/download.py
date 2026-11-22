# Mengunduh teks 604 halaman + font glyph per halaman ke assets (agar APK offline)
import os, urllib.request as u
from concurrent.futures import ThreadPoolExecutor
A = 'https://api.quran.com/api/v4'
D = 'app/src/main/assets/'
os.makedirs(D + 'data', exist_ok=True); os.makedirs(D + 'fonts', exist_ok=True)
def get(url):
    err = None
    for _ in range(4):
        try: return u.urlopen(u.Request(url, headers={'User-Agent': 'Mozilla/5.0'}), timeout=60).read()
        except Exception as e: err = e
    raise err
open(D + 'data/chapters.json', 'wb').write(get(A + '/chapters?language=id'))
def pg(p):
    open(f'{D}data/p{p}.json', 'wb').write(get(f'{A}/verses/by_page/{p}?mushaf=2&words=true&word_fields=code_v1,line_number&per_page=50'))
    open(f'{D}fonts/p{p}.woff2', 'wb').write(get(f'https://verses.quran.com/fonts/quran/hafs/v1/woff2/p{p}.woff2'))
with ThreadPoolExecutor(8) as x: list(x.map(pg, range(1, 605)))
print('selesai')
