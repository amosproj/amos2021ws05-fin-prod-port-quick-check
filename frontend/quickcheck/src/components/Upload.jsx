import React, { useState, useRef } from "react";

import { IconButton } from "@chakra-ui/react";
import { notification } from "../utils/notification";
import { AttachmentIcon } from '@chakra-ui/icons';

export default function UploadButton(buttonProps) {
  const [image, setImage] = useState("");
  const inputFile = useRef(null);

  const handleFileUpload = e => {
    const { files } = e.target;
    if (files && files.length) {
      const filename = files[0].name;

      var parts = filename.split(".");
      const fileType = parts[parts.length - 1];
      console.log("fileType", fileType); //ex: zip, rar, jpg, svg etc.
      notification("Upload succeeded", "", "success")
      setImage(files[0]);
    }
  };

  const onButtonClick = () => {
    inputFile.current.click();
  };

  console.log("imageimage", image);
  return (
    <div>
      <input
        style={{ display: "none" }}
        // accept=".zip,.rar"
        ref={inputFile}
        onChange={handleFileUpload}
        type="file"
      />
      <div className="button" onClick={onButtonClick}>
      <IconButton {...buttonProps} icon={<AttachmentIcon/>}/>
      </div>
    </div>
  );
};

