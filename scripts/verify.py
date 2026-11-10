from pathlib import Path
import xml.etree.ElementTree as ET
root = Path(__file__).resolve().parents[1]
required = [
    'settings.gradle.kts', 'build.gradle.kts', 'app/build.gradle.kts',
    'app/src/main/AndroidManifest.xml',
    'app/src/main/java/com/zbinmy/folkplaytv/MainActivity.kt',
    'app/src/main/res/values/styles.xml',
]
missing = [p for p in required if not (root / p).is_file()]
if missing:
    raise SystemExit('Missing files: ' + ', '.join(missing))
for p in root.rglob('*.xml'):
    ET.parse(p)
manifest = ET.parse(root/'app/src/main/AndroidManifest.xml').getroot()
ns = '{http://schemas.android.com/apk/res/android}'
activities = [a.attrib.get(ns+'name') for a in manifest.find('application').findall('activity')]
assert '.MainActivity' in activities and '.GameActivity' in activities, activities
assert 'android.hardware.touchscreen' in (root/'app/src/main/AndroidManifest.xml').read_text()
print('Project structure and XML validation passed.')
