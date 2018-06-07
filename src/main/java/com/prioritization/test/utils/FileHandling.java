package com.prioritization.test.utils;

import com.prioritization.test.domain.FaultMatrix;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.*;

public class FileHandling {

    public static FaultMatrix readMatrix(String fileName) throws IOException {
        String extension = FilenameUtils.getExtension(fileName);
        if (extension.equals("txt")) {
            Scanner input = new Scanner(new File(fileName));
            // pre-read in the number of rows/columns
            int rows = 0;
            int columns = 0;
            int elements = 0;
            while (input.hasNextLine()) {
                ++rows;
                Scanner colReader = new Scanner(input.nextLine());
                while (colReader.hasNextByte()) {
                    ++elements;
                    colReader.next();
                }
            }
            columns = elements / rows;

            int[][] a = new int[rows][columns];

            input.close();

            // read in the data
            input = new Scanner(new File(fileName));
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < columns; ++j) {
                    if (input.hasNextByte()) {
                        a[i][j] = input.nextInt();
                    }
                }
            }
            input.close();
            FaultMatrix faultMatrix = new FaultMatrix(columns, rows, a);
            return faultMatrix;
        }else if(extension.equals("csv")) {


            File file = new File(fileName);

            BufferedReader bufRdr = new BufferedReader(new FileReader(file));
            String line = null;
            int row = 0;
            int col = 0;

            int[][] a = new int[1000][1000];


            //read each line of text file
            while ((line = bufRdr.readLine()) != null) {
                col = 0;
                StringTokenizer st = new StringTokenizer(line, ",");
                while (st.hasMoreTokens()) {
                    //get next token and store it in the array
                    a[row][col] = Integer.parseInt(st.nextToken());
                    col++;
                }
                row++;
            }
            a = (int[][]) resizeArray(a, row);

            for (int i = 0; i < a.length; i++) {
                if (a[i] == null)
                    a[i] = new int[col];
                else a[i] = (int[]) resizeArray(a[i], col);
            }
            bufRdr.close();
            FaultMatrix faultMatrix = new FaultMatrix(col, row, a);
            return faultMatrix;
        }else if(extension.equals("xls")){
            int[][] a= new int[1000][1000];

            FileInputStream fis = new FileInputStream(fileName);
                // Create an excel workbook from the file system.
                HSSFWorkbook workbook = new HSSFWorkbook(fis);
                // Get the first sheet on the workbook.
                HSSFSheet sheet = workbook.getSheetAt(0);

                Iterator rows = sheet.rowIterator();
                int col=0;
                int rowe=0;
                while (rows.hasNext()) {
                    HSSFRow row = (HSSFRow) rows.next();
                    Iterator cells = row.cellIterator();
                    col=0;
                    while (cells.hasNext()) {
                        HSSFCell cell = (HSSFCell) cells.next();
                        a[rowe][col]=(int)cell.getNumericCellValue();
                        col++;
                    }
                    rowe++;
                }
            a = (int[][]) resizeArray(a, rowe);

            for (int i = 0; i < a.length; i++) {
                if (a[i] == null)
                    a[i] = new int[col];
                else a[i] = (int[]) resizeArray(a[i], col);
            }
            FaultMatrix faultMatrix = new FaultMatrix(col, rowe, a);
            fis.close();
            return faultMatrix;
        }else{
            throw new FileSystemException("Not a valid extenstion");
        }
    }

    private static Object resizeArray (Object oldArray, int newSize) {
        int oldSize = java.lang.reflect.Array.getLength(oldArray);
        Class elementType = oldArray.getClass().getComponentType();
        Object newArray = java.lang.reflect.Array.newInstance(
                elementType, newSize);
        int preserveLength = Math.min(oldSize, newSize);
        if (preserveLength > 0)
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        return newArray; }
}
