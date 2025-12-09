## 📝 Tomcat Access Log Custom Valve 

---

### **✨ 제목: `tomcat localhostaccesslogfilter` (커스텀 Valve 사용)`**

이 문서는 Tomcat의 Access Log에서 특정 속성을 **마스킹**하거나 **추가적인 전처리**를 수행하기 위한 **커스텀 Valve(Custom Valve)**를 빌드하고 배포하는 방법을 안내합니다.

---

### **🚀 How to Use (사용 방법)**

#### **1. 커스텀 Valve 빌드 및 배포**

아래 PowerShell 코드는 커스텀 클래스(`MaskingAttributeValve.java`)를 컴파일하고, JAR 파일로 패키징한 후, Tomcat의 라이브러리(`lib`) 디렉토리에 배포하는 법을 설명합니다.

**✅ 필수 준비 사항:**
* `JDK (Java Development Kit)`가 설치되어 있어야 합니다.
* 컴파일에 필요한 Tomcat 라이브러리(`tomcat-juli.jar`, `catalina.jar` 등)가 스크립트 실행 경로의 **`lib`** 디렉토리에 위치해야 합니다.
* **`MaskingAttributeValve.java`** 파일이 **`customvalve`** 디렉토리 내에 존재해야 합니다.

**💻 PowerShell 스크립트 (복사-붙여넣기 사용):**

```powershell
# 1. 커스텀 밸브 소스 코드를 담을 디렉토리 생성
mkdir customvalve

# 2. MaskingAttributeValve.java 파일을 customvalve 디렉토리에 위치시킵니다.
#    (이 단계는 수동으로 진행하거나 별도의 명령을 사용해야 합니다.)

# 3. 자바 컴파일러를 사용하여 소스 코드 컴파일
#    -cp "lib\*" : 컴파일 시 필요한 Tomcat의 라이브러리 (lib 디렉토리에 있는 모든 JAR)를 클래스패스에 포함합니다.
#    "C:\Program Files\Java\jdk-25\bin\javac.exe" 경로는 사용자 환경에 맞게 수정해야 합니다.
& "C:\Program Files\Java\jdk-25\bin\javac.exe" `
-cp "lib\*" customvalve\MaskingAttributeValve.java

# 4. 컴파일된 클래스 파일을 JAR 파일로 패키징
& "C:\Program Files\Java\jdk-25\bin\jar.exe" -cvf my-custom-valve.jar customvalve

# 5. 생성된 JAR 파일을 Tomcat의 라이브러리 디렉토리로 이동 (배포)
#    - TOMCAT_HOME은 Tomcat 설치 경로로 대체해야 합니다.
mv my-custom-valve.jar TOMCAT_HOME/lib
```

**server.xml 적용 예시**
```
        <Valve className="customvalve.MaskingAttributeValve" />
        <Valve className="org.apache.catalina.valves.AccessLogValve"
               directory="logs"
               prefix="localhost_access_log"
               suffix=".txt"
               pattern="%h %l %u %t &quot;%m %{maskedUriForLog}r %H&quot; %s %b" />
```
