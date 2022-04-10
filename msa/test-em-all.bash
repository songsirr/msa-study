#!/usr/bin/env bash
#
# Sample usage:
#
#   HOST=localhost PORT=7000 ./test-em-all.bash
#
: ${HOST=localhost}
: ${PORT=7002}

function assertCurl() {

  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
  local result=$(eval $curlCmd)
  local httpCode="${result:(-3)}"
  RESPONSE='' && (( ${#result} > 3 )) && RESPONSE="${result%???}"

  if [ "$httpCode" = "$expectedHttpCode" ]
  then
    if [ "$httpCode" = "200" ]
    then
      echo "Test OK (HTTP Code: $httpCode)"
    else
      echo "Test OK (HTTP Code: $httpCode, $RESPONSE)"
    fi
  else
      echo  "Test FAILED, EXPECTED HTTP Code: $expectedHttpCode, GOT: $httpCode, WILL ABORT!"
      echo  "- Failing command: $curlCmd"
      echo  "- Response Body: $RESPONSE"
      exit 1
  fi
}

function assertEqual() {

  local expected=$1
  local actual=$2

  if [ "$actual" = "$expected" ]
  then
    echo "Test OK (actual value: $actual)"
  else
    echo "Test FAILED, EXPECTED VALUE: $expected, ACTUAL VALUE: $actual, WILL ABORT"
    exit 1
  fi
}
set -e

echo "HOST=${HOST}"
echo "PORT=${PORT}"


# Verify that a normal request works, expect three posts
assertCurl 200 "curl http://$HOST:$PORT/management/1 -s"
assertEqual 1 $(echo $RESPONSE | jq .user.id)
assertEqual 3 $(echo $RESPONSE | jq ".posts | length")

# Verify that a 404 (Not Found) error is returned for a non existing userId (404)
assertCurl 404 "curl http://$HOST:$PORT/management/404 -s"

# Verify that a 422 (Unprocessable Entity) error is returned for a userId 0
assertCurl 422 "curl http://$HOST:$PORT/management/0 -s"
assertEqual "\"Invalid id:0\"" "$(echo $RESPONSE | jq .message)"