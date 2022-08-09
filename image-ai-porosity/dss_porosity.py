from PyQt5 import QtWidgets, uic, QtCore, QtGui

import sys, os, time, functools, asyncio
import numpy as np
import cv2
import sqlite3
import PIL.Image as Img
import PIL.ImageEnhance as Enhance
import json

class ProcessingThread(QtCore.QThread):
    current_signal = QtCore.pyqtSignal(np.ndarray)
    cap = None
    pause = True

    def run(self):
        while True:
            if not self.pause:
                if self.cap and self.cap.isOpened():
                    ret, frame = self.cap.read()

                    if frame is None:
                        self.pause = True
                        continue

                    frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

                    # current = gui.QImage(frame.data, frame.shape[1], frame.shape[0], gui.QImage.Format_RGB888)
                    self.current_signal.emit(frame)

                else:
                    print('VideoCapture is None')

class UibdDialog(QtWidgets.QMainWindow):
        
    def load_materials(self):
        conn = sqlite3.connect(self.db_name)
        cur = conn.cursor()
        self.materials = cur.execute('SELECT * FROM Materials').fetchall()
        conn.commit()
        conn.close()

    def push_button_add_click(self):
        material_name = self.nameMaterial.text()
        material_area = self.squarePore.text()
        material_area_std = self.deviationSquare.text()
        material_porous = self.porisity.text()
        material_porous_std = self.deviationPorisity.text()

        data = [material_name, material_area, material_area_std, material_porous, material_porous_std]

        flag = [True if (m is not None and m != '') else False for m in data ]

        if flag:
            try:
                material_area = float(material_area)
                material_area_std = float(material_area_std)
                material_porous = float(material_porous)
                material_porous_std = float(material_porous_std)
                connect = sqlite3.connect(self.db_name)
                crsr = connect.cursor()
                crsr.execute("""INSERT INTO Materials(NAME,
                PORE_AREA_MEAN, PORE_AREA_STD, POROUS_MEAN, POROUS_STD)
                VALUES (?,?,?,?,?)""", (material_name, material_area, material_area_std, material_porous, material_porous_std))
                connect.commit()
                connect.close()
                self.load_materials()
                self.fill_table()

            except Exception as e:
                print(e)


    def push_button_delete_click(self):
        index = self.numRecord.text()
        try:
            index = int(index) - 1
            if 0 <= index <= len(self.materials)-1:
                connect = sqlite3.connect(self.db_name)
                crsr = connect.cursor()
                row = self.materials.pop(index)
                id = row[0]
                crsr.execute('DELETE FROM Materials WHERE ID=?',(id,))
                connect.commit()
                connect.close()
                self.load_materials()
                self.fill_table()
        except Exception as e:
            print(e)

        self.numRecord.setText('')
        self.numRecord.clear()

    def push_button_ok_click(self):
        self.parent.materialBox.clear()
        self.load_materials()
        for row in self.materials:
            self.parent.materialBox.addItem(str(row[1]))
 
        self.parent.materialBox.activated.connect(self.parent.onComboChanged)
        self.parent.onComboChanged(0, False)
        self.save_table()
        self.parent.onComboChanged(0, False)
        self.close()    

    def fill_table(self):
        while self.bdWindow.rowCount() > 0:
            self.bdWindow.removeRow(0)

        self.bdWindow.setColumnCount(6)
        self.bdWindow.setRowCount(len(self.materials))

        self.bdWindow.setHorizontalHeaderLabels(['ID', 'Наименование', 'Площадь поры', 'Откл. от площади',
                                                    'Пористость', 'Откл. от пористости'])

        self.bdWindow.horizontalHeaderItem(0).setToolTip("ID записи в базе данных")
        self.bdWindow.horizontalHeaderItem(1).setToolTip("Наименование материала")
        self.bdWindow.horizontalHeaderItem(2).setToolTip("Нормальная площадь поры")
        self.bdWindow.horizontalHeaderItem(3).setToolTip("Отклонение от нормы площади поры")
        self.bdWindow.horizontalHeaderItem(4).setToolTip("Нормальная пористость")
        self.bdWindow.horizontalHeaderItem(5).setToolTip("Отклонение от нормы пористости")

        for i, row in enumerate(self.materials):
            self.bdWindow.setItem(i, 0, QtWidgets.QTableWidgetItem(str(row[0])))
            self.bdWindow.setItem(i, 1, QtWidgets.QTableWidgetItem(str(row[1])))
            self.bdWindow.setItem(i, 2, QtWidgets.QTableWidgetItem(str(row[2])))
            self.bdWindow.setItem(i, 3, QtWidgets.QTableWidgetItem(str(row[3])))
            self.bdWindow.setItem(i, 4, QtWidgets.QTableWidgetItem(str(row[4])))
            self.bdWindow.setItem(i, 5, QtWidgets.QTableWidgetItem(str(row[5])))

        # делаем ресайз колонок по содержимому
        self.bdWindow.resizeColumnsToContents()

    def save_table(self):
        self.materials = []
        self.parent.materials = []
        for i in range(self.bdWindow.rowCount()):
            row = []
            row.append(int(self.bdWindow.item(i, 0).text()))
            row.append(self.bdWindow.item(i, 1).text())
            row.append(float(self.bdWindow.item(i, 2).text()))
            row.append(float(self.bdWindow.item(i, 3).text()))
            row.append(float(self.bdWindow.item(i, 4).text()))
            row.append(float(self.bdWindow.item(i, 5).text()))
            self.materials.append(row)
            self.parent.materials.append(row)

        conn = sqlite3.connect(self.db_name)
        cur = conn.cursor()
        cur.execute('DELETE FROM Materials')
        
        for row in self.materials:
            cur.execute("""INSERT INTO Materials(NAME,
            PORE_AREA_MEAN, PORE_AREA_STD, POROUS_MEAN, POROUS_STD)
            VALUES (?,?,?,?,?)""", (row[1], row[2], row[3], row[4], row[5]))
        
        conn.commit()
        conn.close()

    def __init__(self, parent=None, db_name='porousquality.db'):
        super(UibdDialog, self).__init__(parent)
        self.db_name = db_name
        self.parent = parent
        # loading
        uic.loadUi('D:/Study/project_porosity/uibd.ui', self) # Load the .ui file
        self.load_materials()
        #print(self.materials)
        self.fill_table()

        self.addButton.clicked.connect(self.push_button_add_click) # Обработчик кнопки Добавить
        self.deleteButton.clicked.connect(self.push_button_delete_click) # Обработчик кнопки Удалить
        self.okButton.clicked.connect(self.push_button_ok_click)

class UispprWindow(QtWidgets.QMainWindow):

    def explore(self, image):
        """
        Входной аргумент:
        image - исследуемое изображение
        Выход:
        image - изображение с контурами пор
        area_c - отношение площади всех пор ко всей площади изображения (пористость)
        len(bad_conrours) - количество 'плохих' пор
        """
        image = np.copy(image)
        # дополнительная обработка шумов
        blured = cv2.GaussianBlur(image, (5, 5), 0)
        # конвертация BGR формата в формат HSV
        hsv = cv2.cvtColor(blured, cv2.COLOR_BGR2HSV)
        lower_black = np.array([0, 0, 0])
        upper_black = np.array([120, 120, 120])
        # определяем маску для обнаружения контуров пор.
        # будут выделены поры в заданном диапозоне
        mask = cv2.inRange(hsv, lower_black, upper_black)

        # получаем массив конутров
        contours, _ = cv2.findContours(mask, cv2.RETR_TREE, cv2.CHAIN_APPROX_NONE)   # first _ are useless
        
        good_contours = []
        bad_contours = []
        area_c = 0
        # находим 'хорошие' и 'плохие' поры
        for contour in contours:
            # также подсчитываем общую площадь пор
            area_c += cv2.contourArea(contour)
            if self.mat_area - self.mat_area_std <= cv2.contourArea(contour) <= self.mat_area + self.mat_area_std:
                good_contours.append(contour)
            else:
                bad_contours.append(contour)
        area_c = area_c / (image.shape[0] * image.shape[1])
        # выделяем 'хорошие' поры зеленым цветом
        cv2.drawContours(image, good_contours, -1, (0, 255, 0), 3)
        # выделяем 'плохие' поры красным цветом
        cv2.drawContours(image, bad_contours, -1, (255, 0, 0), 3)
        return image, area_c, len(bad_contours)

    @QtCore.pyqtSlot(np.ndarray)
    def set_current_frame(self, image):
        ''' Converts a QImage into an opencv MAT format '''
        self.set_original_frame(image)

    def set_original_frame(self, image):
        self.origin_img = cv2.resize(image, dsize=(300, 300))
        image = QtGui.QImage(self.origin_img.data, self.origin_img.shape[1], self.origin_img.shape[0], QtGui.QImage.Format_RGB888)
        pixmap = QtGui.QPixmap.fromImage(image)
        scene = QtWidgets.QGraphicsScene(self)
        item = QtWidgets.QGraphicsPixmapItem(pixmap)
        scene.addItem(item)
        self.imageView.setScene(scene)

    def set_transformed_frame(self, image):
        # image = Img.fromarray(image)
        # image = Enhance.Contrast(image).enhance(self.contrastSlider.value() / 10)
        # image = Enhance.Brightness(image).enhance(self.exposureSlider.value() / 10)
        # image = Enhance.Sharpness(image).enhance(self.sharpnessSlider.value() / 10)
        image = np.array(image)
        im_gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        th, image = cv2.threshold(im_gray, int(self.binarSlider.value()), 255, cv2.THRESH_BINARY)
        self.transform_img = image
        image = QtGui.QImage(image.data, image.shape[1], image.shape[0], image.shape[1], QtGui.QImage.Format.Format_Grayscale8)
        # pixmap = QtGui.QPixmap.fromImage(image)
        self.imageAnalysisView.setPixmap(QtGui.QPixmap.fromImage(image).scaled(self.imageAnalysisView.size()))
        # scene = QtWidgets.QGraphicsScene(self)
        # item = QtWidgets.QGraphicsPixmapItem(pixmap)
        # scene.addItem(item)
        # self.imageAnalysisView.setScene(scene)

    def set_result_frame(self, image):
        self.result_img = image
        image, pore, bad_pores = self.explore(image)
        
        image = QtGui.QImage(image.data, image.shape[1], image.shape[0], QtGui.QImage.Format_Grayscale8)
        if self.mat_porous - self.mat_porous_std <= pore <= self.mat_porous + self.mat_porous_std:
            pore_rep = 'в норме'
            self.numporisity.setStyleSheet('color: green; font-size: 10pt;')
            self.porisity.setStyleSheet('color: green; font-size: 10pt;')
        else:
            pore_rep = 'не в норме'
            self.numporisity.setStyleSheet('color: red; font-size: 10pt;')
            self.porisity.setStyleSheet('color: red; font-size: 10pt;')
        self.numporisity.setText(str(round(pore, 8))); self.numporisity.adjustSize()
        self.porisity.setText(str(pore_rep)); self.porisity.adjustSize()
        self.numPores.setText(str(bad_pores)); self.numPores.adjustSize()
        pixmap = QtGui.QPixmap.fromImage(image)
        scene = QtWidgets.QGraphicsScene(self)
        item = QtWidgets.QGraphicsPixmapItem(pixmap)
        scene.addItem(item)
        self.poreView.setScene(scene)

    def contrast_changed(self):
        self.set_transformed_frame(self.origin_img)
        self.set_result_frame(self.transform_img)

    def brightness_changed(self):
        self.set_transformed_frame(self.origin_img)
        self.set_result_frame(self.transform_img)

    def sharpness_changed(self):
        self.set_transformed_frame(self.origin_img)
        self.set_result_frame(self.transform_img)

    def binar_changed(self):
        self.set_transformed_frame(self.origin_img)
        self.set_result_frame(self.transform_img)

    def UibdDialog_show(self):
        uibddialog = UibdDialog(self, self.db_name)
        uibddialog.show()

    def set_via_webcam(self):
        if self.thread is None:
            self.thread = ProcessingThread(self)
            self.thread.current_signal.connect(self.set_current_frame)
            self.thread.start()
            
        if self.thread.cap is None:
            self.thread.cap = cv2.VideoCapture(0)
        if self.thread.pause is True:
            self.shootButton.setEnabled(True)
            self.thread.pause = False

    def closeEvent(self, a0: QtGui.QCloseEvent):
        if self.thread is not None:
            self.thread.pause = True
            if self.thread.cap is not None:
                self.thread.cap.release()
            self.thread.exit(0)

    def shoot_button_click(self):
        if self.thread.pause:
            self.thread.pause = False
        else:
            self.thread.pause = True
            self.set_transformed_frame(self.origin_img)
            self.set_result_frame(self.origin_img)

    def load_materials(self):
        conn = sqlite3.connect(self.db_name)
        cur = conn.cursor()
        self.materials = cur.execute('SELECT * FROM Materials').fetchall()
        conn.close()

    def get_material_by_name(self, mat_name):
        conn = sqlite3.connect(self.db_name)
        cur = conn.cursor()
        res = cur.execute('SELECT * FROM Materials WHERE NAME = "{}"'.format(mat_name)).fetchall()
        conn.close()
        return res

    def material_selected(self, index, set_res_fr=True):
        self.update_data(index)
        self.material_area.setText('Площадь поры:{}'.format(str(self.mat_area)))
        self.material_std_area.setText('Откл. от площади:{}'.format(str(self.mat_area_std)))
        self.material_porous.setText('Пористость:{}'.format(str(self.mat_porous)))
        self.material_std_porous.setText('Откл. от порист.:{}'.format(str(self.mat_porous_std)))
        if set_res_fr:
            self.set_result_frame(self.transform_img)

    def onOpenFile(self): # обработчик нажатия Исходные данные для анализа->Open
        if self.thread:
            self.pushButton.setEnabled(False)
            self.thread.pause = True
            if self.thread.cap is not None:
                self.thread.cap.release()
                self.thread.cap = None
        filename, _ = QtWidgets.QFileDialog.getOpenFileName(self, "Open file", '') 
        if filename != '':
            try:
                origin_img = Img.open(filename)
                origin_img = np.array(origin_img)
                self.set_original_frame(origin_img)
                self.set_transformed_frame(self.origin_img)
                self.set_result_frame(self.origin_img)
            except Exception as ex:
                print(ex)
            # PROCESS

    def onComboChanged(self, index, set_res_fr=True): # показ иформации о выбранном материале
        self.update_data(index)
        #print('current material index =', index)
        self.label_13.setText(str(self.mat_area)); self.label_13.adjustSize()
        self.label_15.setText(str(self.mat_area_std)); self.label_15.adjustSize()
        self.label_17.setText(str(self.mat_porous)); self.label_17.adjustSize()
        self.label_19.setText(str(self.mat_porous_std)); self.label_19.adjustSize()

    def update_data(self, id: int):
        if len(self.materials) != 0:
            row = self.materials[np.min([np.max([0, id]), len(self.materials) - 1])]
            self.mat_name = row[1]
            self.mat_area = row[2]
            self.mat_area_std = row[3]
            self.mat_porous = row[4]
            self.mat_porous_std = row[5]
        else:
            self.mat_name = 'Не задано'
            self.mat_area = 0
            self.mat_area_std = 0
            self.mat_porous = 0
            self.mat_porous_std = 0

    def create_db_if_not_exist(self):
        if not os.path.isfile(self.db_name):
            rows = [
                (0, 'Материал2', 12.0, 5.0, 0.1, 0.01),
                (1, 'Материал3', 9.00, 8.0, 0.15, 0.01),
                (2, 'Материал4', 15.0, 8.0, 0.2, 0.5),
                (3, 'Материал5', 14.0, 7.0, 0.3, 0.7),
            ]
            conn = sqlite3.connect(self.db_name)
            cur = conn.cursor()
            cur.execute("""CREATE TABLE Materials 
                            (ID	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                            NAME TEXT,
                            PORE_AREA_MEAN REAL NOT NULL,
                            PORE_AREA_STD REAL NOT NULL,
                            POROUS_MEAN REAL NOT NULL,
                            POROUS_STD REAL NOT NULL
                            )""")
            cur.executemany("""INSERT INTO Materials values (?,?,?,?,?,?)""", rows)
            conn.commit()
            conn.close()

    def __init__(self):
        super(UispprWindow, self).__init__() # Call the inherited classes __init__ method
        self.db_name = 'porousquality.db'
        self.create_db_if_not_exist() # если бд не найдена, то создать ее
        # loading ui
        uic.loadUi('D:/Study/project_porosity/uisppr.ui', self) # Load the .ui file
        self.changeButton.clicked.connect(self.UibdDialog_show) # Обработчик кнопки "Изменить"

        self.load_materials()

        for row in self.materials:
            self.materialBox.addItem(str(row[1]))
        self.materialBox.activated.connect(self.onComboChanged)
        self.onComboChanged(0, False)

        self.shootButton.clicked.connect(self.shoot_button_click)    
        self.shootButton.setEnabled(False)

        self.setWindowTitle("Quality porous material")
        self.thread = None

        self.actionOpen.triggered.connect(self.onOpenFile)
        self.actionWebCam.triggered.connect(self.set_via_webcam)

        # ползунок контрастности
        self.contrastSlider.setRange(-200, 200)
        self.contrastSlider.setTickPosition(QtWidgets.QSlider.TicksBothSides)
        self.contrastSlider.setValue(10)
        self.contrastSlider.valueChanged.connect(self.contrast_changed)

        # ползунок яркости
        self.exposureSlider.setRange(-200, 200)
        self.exposureSlider.setTickPosition(QtWidgets.QSlider.TicksBothSides)
        self.exposureSlider.setValue(10)
        self.exposureSlider.valueChanged.connect(self.brightness_changed)

        # ползунок резкости
        self.sharpnessSlider.setRange(-200, 200)
        self.sharpnessSlider.setTickPosition(QtWidgets.QSlider.TicksBothSides)
        self.sharpnessSlider.setValue(10)
        self.sharpnessSlider.valueChanged.connect(self.sharpness_changed)

        # ползунок бинаризации
        self.binarSlider.setRange(-200, 200)
        self.binarSlider.setTickPosition(QtWidgets.QSlider.TicksBothSides)
        self.binarSlider.setValue(10)
        self.binarSlider.valueChanged.connect(self.binar_changed)
        
class App(QtWidgets.QApplication):
    def __init__(self, *args):
        super(App, self).__init__(*args)
        self.main = UispprWindow()
        self.main.show()

if __name__ == "__main__":
    app = App(sys.argv)
    sys.exit(app.exec())