%dw 2.0
fun stringifyAny(inputData: Any) =
    if (isEmpty(inputData.^) or
        inputData.^mimeType == "*/*")
        inputData
    else if (inputData.^mimeType == "application/xml" or
             inputData.^mimeType == "application/dw" or
             inputData.^mimeType == "application/json")
        write(inputData,inputData.^mimeType,{indent:false})
    else
        write(inputData,inputData.^mimeType)
						   	
fun stringifyNonJSON(inputData: Any) =
    if (isEmpty(inputData.^) or
        inputData.^mimeType == "application/json" or
        inputData.^mimeType == "*/*")
        inputData
    else if (inputData.^mimeType == "application/xml" or
             inputData.^mimeType == "application/dw")
        write(inputData,inputData.^mimeType, {indent:false})
    else
        write(inputData,inputData.^mimeType)

fun stringifyAnyWithMetadata(inputData: Any) = { 
    data:
        if (isEmpty(inputData.^) or
            inputData.^mimeType == "*/*")
            inputData
        else if (inputData.^mimeType == "application/xml" or
                 inputData.^mimeType == "application/dw" or
                 inputData.^mimeType == "application/json")
            write(inputData, inputData.^mimeType, {indent:false})
        else
            write(inputData, inputData.^mimeType),
    (contentLength: inputData.^contentLength) if (inputData.^contentLength != null),
    (dataType: inputData.^mimeType) if (inputData.^mimeType != null),
    (class: inputData.^class) if (inputData.^class != null)
} 

fun stringifyNonJSONWithMetadata(inputData: Any) = { 
    data:
        if (isEmpty(inputData.^) or
            inputData.^mimeType == "application/json" or
            inputData.^mimeType == "*/*")
            inputData
        else if (inputData.^mimeType == "application/xml" or
                 inputData.^mimeType == "application/dw")
            write(inputData, inputData.^mimeType, {indent:false})
        else
            write(inputData, inputData.^mimeType),
    (contentLength: inputData.^contentLength) if (inputData.^contentLength != null),
    (dataType: inputData.^mimeType) if (inputData.^mimeType != null),
    (class: inputData.^class) if (inputData.^class != null)
} 
