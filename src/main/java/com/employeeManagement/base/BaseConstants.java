package com.employeeManagement.base;

import org.springframework.http.MediaType;

public interface BaseConstants {
    //================================== *** ==================================
    //									URL
    //================================== *** ==================================
    String APP_ENDPOINT = "/api/";
    String PRIVATE_APP_ENDPOINT = APP_ENDPOINT + "private/";
    String PUBLIC_APP_ENDPOINT = APP_ENDPOINT + "public/";

    // module URL
    String APPS_END_POINT = PRIVATE_APP_ENDPOINT + "apps/";
    String APPS_PUBLIC_END_POINT = PUBLIC_APP_ENDPOINT + "apps/";


    /*media type*/
    String EXTERNAL_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;


    /*pageable*/
    String PAGEABLE_PAGE = "page";
    String PAGEABLE_SIZE = "size";
    String PAGEABLE_SEARCH_VALUE = "searchValue";
    String PAGEABLE_PATH = "pageable/{" + PAGEABLE_PAGE + "}/{" + PAGEABLE_SIZE + "}/{" + PAGEABLE_SEARCH_VALUE + "}";
    String PAGEABLE_DATA_PATH = "pageable-data";

    String LIST_DATA_PATH = "list-data";
    /*find by id*/
    String OBJECT_ID = "id";
    String GET_OBJECT_BY_ID = "get-by-id/{" + OBJECT_ID + ":[0-9]*}";
    /*others*/
    String ACTIVE_PATH = "active";
    String DROPDOWN_LIST_PATH = "dropdown-list";


    //================================== *** ==================================
    //									PATTERN
    //================================== *** ==================================

    String STRING_VALIDATION_PATTERN_LIMIT_50 = "^[A-Za-z0-9]{0,50}$";
    String STRING_VALIDATION_PATTERN_LIMIT_100 = "^[A-Za-z0-9]{0,100}$";
    String STRING_WITH_UNDERSCORE_AND_DASH_VALIDATION_PATTERN_LIMIT_50 = "^[A-Za-z0-9_-]{0,50}$";
    String STRING_WITH_UNDERSCORE_AND_DASH_VALIDATION_PATTERN_LIMIT_100 = "^[A-Za-z0-9_-]{0,100}$";
    String MONTH_YEAR_PATTERN = "^[0-9]{6}$";

    //================================== *** ==================================
    //									FILE
    //================================== *** ==================================
    String KEY_FILE_LOCATION = "FILE_LOCATION";
    String KEY_FILE_NAME = "FILE_NAME";

    //================================== *** ==================================
    //									Server Message
    //================================== *** ==================================
    String SAVE_MESSAGE = "Successfully Saved";
    String SAVE_MESSAGE_BN = "সফলভাবে সংরক্ষণ করা হয়েছে";

    String SUBMIT_MESSAGE = "submitted Successfully";
    String SUBMIT_MESSAGE_BN = "রসফলভাবে সাবমিট করা হয়েছে";


    String RESUBMITED_SUCCESS_MESSAGE = "Resubmitted Successfully";
    String RESUBMITED_SUCCESS_MESSAGE_BN = "সফলভাবে পুনরায় জমা দেওয়া হয়েছে";

    String SEND_MESSAGE = "Successfully Sent";
    String SEND_MESSAGE_BN = "সফলভাবে পাঠানো হয়েছে";

    String APPROVE_SUCCESS_MESSAGE = "Approved Successfully";
    String APPROVE_SUCCESS_MESSAGE_BN = "সফলভাবে অনুমোদিত হয়েছে";
    String FORWARD_SUCCESS_MESSAGE = "Forwarded Successfully";
    String BACK_SUCCESS_MESSAGE = "Sent back Successfully";
    String REJECT_SUCCESS_MESSAGE = "Rejected Successfully";
    String REJECT_SUCCESS_MESSAGE_BN = "সফলভাবে প্রত্যাখ্যাত হয়েছে";

    String RETURN_SUCCESS_MESSAGE = "Returned Successfully";
    String RETURN_SUCCESS_MESSAGE_BN = "সফলভাবে পুনরায় পাঠানো হয়েছে";

    String FORWARD_SUCCESS_MESSAGE_BN = "সফলভাবে ফরোয়ার্ড করা হয়েছে";
    String BACK_SUCCESS_MESSAGE_BN = "সফলভাবে ফরোয়ার্ড করা হয়েছে";


    String REG_SAVE_MESSAGE = "Registration submitted Successfully";
    String REG_SAVE_MESSAGE_BN = "রেজিস্ট্রেশন সফলভাবে সাবমিট করা হয়েছে";


    String UPDATE_MESSAGE = "Successfully Updated";
    String UPDATE_MESSAGE_BN = "সফলভাবে আপডেট করা হয়েছে";

    String UPLOAD_MESSAGE = "Successfully Uploaded";

    String UPLOAD_MESSAGE_BN = "সফলভাবে আপলোড করা হয়েছে";
    String DELETE_MESSAGE = "Successfully Deleted";
    String DELETE_MESSAGE_BN = "সফলভাবে ডিলিট করা হয়েছে";
    String DELETE_MESSAGE_FAILED = "Delete Failed";
    String DELETE_MESSAGE_FAILED_BN = "ডিলিট সফল হয়নি";
    String DATA_ALRADY_EXISTS_MESSAGE = "Data already exists!!";
    String DATA_ALRADY_EXISTS_MESSAGE_BN = "তথ্যটি ইতিমধ্যে সংরক্ষিত রয়েছে!!";
    String CHILD_RECORD_FOUND = "Child record found !!";
    String CHILD_RECORD_FOUND_BN = "চাইল্ড রেকর্ড ফাউন্ড !!";

    String VALUE_TOO_LARGE_FOR_COLUMN = "Max character limit reached !!";
    String VALUE_TOO_LARGE_FOR_COLUMN_BN = "সর্বাধিক অক্ষর সীমা পৌঁছেছে !!";

    String NON_NUMERIC_CHAR_FOUND = "A non-numeric character was found where a numeric was expected !!";
    String NON_NUMERIC_CHAR_FOUND_BN = "একটি অ-সংখ্যাসূচক অক্ষর পাওয়া গেছে !!";

    String NOT_NULL = "Some required fields cannot be empty !!";
    String NOT_NULL_BN = "কিছু প্রয়োজনীয় ফিল্ড খালি রাখা যাবে না !!";

    String PROCESS_COMPLETE = "Process successfully completed !!";
    String PROCESS_COMPLETE_BN = "প্রক্রিয়া সফলভাবে শেষ হয়েছে  !!";

    String PROCESS_FAILED = "Processing Failed !!";
    String PROCESS_FAILED_BN = "প্রক্রিয়াকরণ ব্যর্থ হয়েছে  !!";

    String SMS_MESSAGE = "SMS Successfully Send";
    String SMS_MESSAGE_BN = "এসএমএস সফলভাবে পাঠানো হয়েছে";

    String EMAIL_MESSAGE = "Email Successfully Send";
    String EMAIL_MESSAGE_BN = "ইমেইল সফলভাবে পাঠানো হয়েছে";

    String EMAIL_VERIFY_MESSAGE = "Email Successfully Verified";
    String EMAIL_VERIFY_MESSAGE_BN = "ইমেইল সফলভাবে যাচাই করা হয়েছে";

    String MOBILE_VERIFY_MESSAGE = "Mobile Successfully Verified";
    String MOBILE_VERIFY_MESSAGE_BN = "মোবাইল সফলভাবে যাচাই করা হয়েছে";

    String VERIFY_MESSAGE = "Successfully Verified";
    String VERIFY_MESSAGE_BN = "সফলভাবে যাচাই করা হয়েছে";

    String INVALID_OTP_MESSAGE = "Invalid OTP";
    String INVALID_OTP_MESSAGE_BN = "অবৈধ ওটিপি";

    String INPUT_VALIDATION_MESSAGE = "Input is not valid!";
    String INPUT_VALIDATION_MESSAGE_BN = "ইনপুট বৈধ নয়!";

    String QUERY_ERROR_MSG = "Data fetching error !!";
    String QUERY_ERROR_MSG_BN = "Data fetching error !!";



    //================================== *** ==================================
    //									Audit
    //================================== *** ==================================
    String AUDIT_SAVE = "SAVE";
    String AUDIT_UPDATE = "UPDATE";
    String AUDIT_DELETE = "DELETE";
    String AUDIT_URL = "URL";
    String AUDIT_LOGIN = "LOGIN";
    String AUDIT_REPORT_PRINT = "REPORT_PRINT";
    String AUDIT_PROVIDER = "PROVIDER";
}
