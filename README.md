# NavOppgave - Nav Ads Public API


## Løsningsbeskrivelse

1. Henter data fra https://arbeidsplassen.nav.no/public-feed/api/v1/ads
2. Kjør main metoden i `NavOppgaveApplication.kt`, den gjør følgende:
   1. Printer ut Prettify JSON i konsollen
   2. Gir mulighet til å gjøre api kall ved å gå på følgende url http://localhost:8080/job/ad
3. Input felter for API kallet
   1. Liste med søkeord (standard er `Java` og `Kotlin`), men tilrettelagt for å søke på andre ord også
   2. Fra dato (`from`) og til dato (`to`). Standard er (fra 6 mnd tilbake og til idag), tilrettelagt for å søke på andre dato også
   3. Søker nøkkelord i følgende felter:
      1. title
      2. jobtitle
      3. description
         1. Jeg søker gjennom alle disse tre med tanke på `title` kan være "Java utvikler", men `description` kanskje ikke nevner Java så for å være helt sikker så søker jeg gjennom alle disse tre
4. Printer ut (returnerer API kall) uke for uke hvor mange treff jeg får på søkeordene og totale antallet til slutt ommsummert for alle ukene