# Sales Report Script

#### Skrypt analizuje dane o sprzedaży przechowywane w pliku JSON i tworzy ich wizualizacje. Wykorzystywane są następujące biblioteki:

    matplotlib.pyplot
    matplotlib.backends.backend_agg.FigureCanvasAgg
    reportlab.pdfgen.canvas
    tkinter
    json
    matplotlib
    datetime.date
    pdoc

## Biblioteki i konfiguracja

#### Ta sekcja importuje niezbędne biblioteki i ustawia niektóre opcje konfiguracyjne dla Matplotlib.

    import matplotlib.pyplot as plt
    from matplotlib.backends.backend_agg import FigureCanvasAgg
    from reportlab.pdfgen import canvas
    import tkinter as tk
    from tkinter import filedialog
    import json
    import matplotlib as mpl
    from datetime import date
    import pdoc
    mpl.rcParams['font.family'] = 'georgia'

## Załaduj dane sprzedaży

#### This section reads in the sales data from the dane.json file and converts it from JSON format to a Python dictionary.

    Open the JSON file for reading
    with open('dane.json', 'r') as file:
    sales_data = json.load(file)

## Sales Data Analysis

#### W tej sekcji zdefiniowano kilka funkcji służących do analizy danych o sprzedaży.
### Uzyskaj wartość produktu

#### This function calculates the total sales value for a given product.

    def get_product_value(sales_data, product_name):
        product_value = 0
        for sale in sales_data:
            if sale["product_name"] == product_name:
                product_value += sale["value"]
        return product_value

### Uzyskaj dzienną wartość produktu

#### Funkcja ta oblicza całkowitą wartość sprzedaży dla danego dnia tygodnia.

    def get_daily_product_value(sales_data, dzien_tygodnia):
        product_value = 0
        for dzien in sales_data:
            d = date.fromisoformat(dzien['date'])
            weekday = d.weekday()
            weekday_name = ['Pon', 'Wt', 'Śr', 'Czw', 'Pt', 'So', 'Nd'][weekday]
            if weekday_name == dzien_tygodnia:
                product_value += dzien["value"]
        return product_value
* Przykładowy wykres
![](https://github.com/Leonidas90/ecommerce/blob/78da0a02e12db506bca8bee426e6fbb61486e5e9/produkty_dni_tyg.png)
### Uzyskaj wartość produktu dla danego wieku klienta

### Funkcja ta oblicza całkowitą wartość sprzedaży dla danego przedziału wiekowego.

    def get_product_value_for_age(sales_data, wiek_min, wiek_maks):
        product_value = 0
        for wiek in sales_data:
            if wiek['customer_age'] >= wiek_min and wiek['customer_age'] < wiek_maks:
                product_value += wiek["value"]
        return product_value

## Wizualizacja danych sprzedażowych

#### W tej części tworzone są wizualizacje danych o sprzedaży z wykorzystaniem Matplotlib.
### Całkowita wartość sprzedaży

#### W tej sekcji obliczana jest łączna wartość sprzedaży wszystkich produktów.

    sprzedaz_ogolem = sum(sale["value"] for sale in sales_data)

### Sprzedaż według produktów

#### W tej sekcji tworzony jest wykres kołowy przedstawiający wartość sprzedaży dla każdego produktu.

sprzedaz_produktow = {'Nazwa_Produktu':[], 'Wartosc_sprzedazy':[]}
    for produkt in products:
        product_value = get_product_value(sales_data, produkt)
        sprzedaz_produktow['Nazwa_Produktu'].append(produkt)
        sprzedaz_produktow['Wartosc_sprzedazy'].append(product_value)


## This part script generates a PDF report using the Python library ReportLab. The report includes a chart created with Matplotlib and text describing the contents of the report.
### Code Explanation
#### Display a file dialog to choose the location to save the PDF

#### This part of the code displays a file dialog to choose the location to save the PDF report.

    root = tk.Tk()
    root.withdraw()
    pdf_file = filedialog.asksaveasfilename(defaultextension='.pdf', 
                                            initialfile='raport_sprzedazy.pdf')

### Create the PDF document

#### This part of the code creates a new PDF document using the canvas.Canvas class from the ReportLab library.

    c = canvas.Canvas(pdf_file)
### Add the chart to the PDF
#### This part of the code adds a chart to the PDF using the c.drawImage method from the canvas.Canvas class.


    c.drawImage('produkty_dni_tyg.png', 100, 50, 500, 370)
### Add text to the PDF

#### This part of the code adds text to the PDF using the c.drawString method from the canvas.Canvas class. The text includes a title, a description of the report, a list of subjects covered in the report, and a total sales figure.

    c.setFont("Times-Roman", 16)
    c.drawString(200, 790, 'Raport sprzedazy sklepu internetowego')
    c.setFont("Times-Roman", 12)
    c.drawString(50, 750, 'To jest podstawowy raport na potrzeby aplikacji zaliczeniowej.')
    c.drawString(50, 725, 'Projekt byl przygotowywany w ramach zaliczenia przedmiotow:')
    c.setFont("Times-Roman", 14)
    c.drawString(50, 690, 'Praktyka Programowania Python')
    c.drawString(50, 660, 'Projektowanie Aplikacji Internetowych - PHP, Python, Javascript')
    c.drawString(50, 630, 'Organizacja i rozwój projektów Open Source')
    c.setFont("Times-Roman", 12)
    c.drawString(50, 600, 'Jest to skrypt napsany w Pythonie')
    c.drawString(50, 575, 'W raporcie za pomoca matplotlib generowane sa trzy wykresy')
    c.drawString(50, 550, 'Wynik jest zapisywany do pliku pdf uzywajac metod biblioteki reportlab')
    c.drawString(50, 525, 'Dane do generacji wykresów zostaly utworzone w pliku JSON')
    c.setFont("Times-Roman", 14)
    c.drawString(50, 450, 'Calkowita sprzedaz w biezacym tygodniu wyniosla: ' + str(sprzedaz_ogolem) + ' PLN')

### Add multiple pages and charts to the PDF

#### This part of the code adds multiple pages to the PDF using the c.showPage method from the canvas.Canvas class. Each page includes a chart created with Matplotlib.

    c.showPage()
    c.drawImage('best_produkty.png', 50, 50, 500, 370)
    c.drawImage('wiek_produkty.png', 50, 420, 500, 370)

### Save the PDF

#### This part of the code saves the PDF report to the location chosen in the file dialog.

    c.save()