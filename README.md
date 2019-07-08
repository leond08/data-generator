## Generate dummy data and Encryption
This script will generate dummy data and also encrypt csv file.

## Requirements

Java 1.8 +

Maven

### Build

Go to data-encryption folder

`mvn clean install`

### Run the app

Copy the jar from target folder

`cp target/data-encryption-1.0-SNAPSHOT.one-jar.jar <path>`


### Command to run the app

`java -jar data-encryption-1.0-SNAPSHOT.one-jar.jar <parameters>`


### Parameters/Usage

```
usage: java -cp . data-encryption [options here]
 -block_size,--blockSize <arg>                Block size for Rijandael
 -encryption_type,--encryptionType <arg>      Encryption type to use
                                              (Rijandael, MD5 only)
 -field_names,--fieldNames <arg>              Field names of data to
                                              generate
 -fields_to_encrypt,--fieldsToEncrypt <arg>   Fields to encrypt
 -generate_data,--generateData <arg>          Set to true to generate
                                              dummy data
 -input_file_path,--inputFilePath <arg>       Input File Path of the csv
 -number_rows,--numberRows <arg>              Number of rows to generate
 -output_file_path,--outputFilePath <arg>     Output File Path of the csv
 -secure_key,--secureKey <arg>                Secure key for Rijandael
 -thread_count,--threadCount <arg>            Number of threads to execute
 -use_temp_files,--useTempFiles <arg>         Use temp files.
 -vector_key,--vectorKey <arg>                Vector key for Rijandael
 
 ```
 
### Sample
 
 Generate dummy data
 
```
java -jar data-encryption-1.0-SNAPSHOT.one-jar.jar -generate_data true -output_file_path dummy.csv -number_rows 10 -field_names lastname,middlename,firstname
```
 
 Encrypt data using MD5 method
 
```
java -jar data-encryption-1.0-SNAPSHOT.one-jar.jar -generate_data false -input_file_path data.csv -output_file_path encrypted.csv -fields_to_encrypt lastname,middlename,firstname encryption_type md5
```

 Using Rijandael Method
 
```
java -jar data-encryption-1.0-SNAPSHOT.one-jar.jar -generate_data false -input_file_path data.csv -output_file_path encrypted.csv -fields_to_encrypt lastname,middlename,firstname encryption_type aes -secure_key TIknkud9HKnvk3fkd9FNVel -vector_key TIknkud9HKnvk3fkd9FNVel -block_size 256
```
 
### Supported File Format
CSV(Comma Separated Value)

### Supported Data type (Generating data)
String